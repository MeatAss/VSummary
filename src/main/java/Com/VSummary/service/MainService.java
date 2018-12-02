package Com.VSummary.service;

import Com.VSummary.domain.entities.MySQL.*;
import Com.VSummary.repository.CommentsRepository;
import Com.VSummary.repository.LikesRepository;
import Com.VSummary.repository.RatingRepository;
import Com.VSummary.repository.SummariesRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
public class MainService {
    @Autowired
    private SummariesRepository summariesRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private RatingRepository ratingRepository;

    public String main(User user, Model model) {
        model.addAttribute("isAuthenticate", !(user == null));
        model.addAttribute("summaries", createListSummaries(user, summariesRepository.findAll()));

        return "main";
    }

    private List<Map<String, Object>> createListSummaries(User user, List<Summaries> summaries){
        List<Map<String, Object>> mapSummaries = new ArrayList<>();

        for (Summaries summary : summaries) {
            mapSummaries.add(createMapSummary(summary, user));
        }

        return mapSummaries;
    }

    private Map<String, Object> createMapSummary(Summaries summary, User user){
        Map<String, Object> mapSummary = new HashMap<>();

        mapSummary.put("id", summary.getId());
        mapSummary.put("nameSummary", summary.getNameSummary());
        mapSummary.put("specialtyNumber", summary.getSpecialtyNumber());
        mapSummary.put("shortDescription", summary.getShortDescription());
        mapSummary.put("textSummary", summary.getTextSummary());

        if (user != null) {
            mapSummary.put("avgRatings", Math.round(
                    summary.getRatings().stream()
                            .mapToDouble(Rating::getMark)
                            .average()
                            .orElse(0)
            ));
            mapSummary.put("comments", createListComments(summary.getComments(), user));
        }

        return mapSummary;
    }

    private List<Map<String, Object>> createListComments(Set<Comments> comments, User user){
        List<Map<String, Object>> mapComments = new ArrayList<>();
        for (Comments comment : comments) {
            mapComments.add(createMapComment(comment, user));
        }

        return mapComments;
    }

    private Map<String, Object> createMapComment(Comments comment, User user){
        Map<String, Object> mapComment = new HashMap<>();

        mapComment.put("id", comment.getId());
        mapComment.put("userName", comment.getUser().getGivenName());
        mapComment.put("message", comment.getMessage());
        mapComment.put("countLikes", comment.getLikes().size());
        mapComment.put("isLike", comment.getLikes().contains(new Likes(user)));

        return mapComment;
    }

    public HttpStatus addComment(User user, long summaryId, String comment) {
        Optional<Summaries> summaries = summariesRepository.findById(summaryId);
        if (!summaries.isPresent())
            return HttpStatus.BAD_REQUEST;

        Set<Comments> comments = summaries.get().getComments();
        Comments newComment = new Comments(comment, user);
        commentsRepository.save(newComment);
        comments.add(newComment);
        summaries.get().setComments(comments);

        summariesRepository.save(summaries.get());

        simpMessagingTemplate.convertAndSend(
                "/topic/main/newComment",
                new JSONObject()
                        .put("message", newComment.getMessage())
                        .put("username", newComment.getUser().getGivenName())
                        .put("summaryId", summaryId)
                        .put("commentId", newComment.getId())
                        .toString());

        return HttpStatus.OK;
    }

    public ResponseEntity<String> addLike(User user, long commentId) {
        Optional<Comments> comment = commentsRepository.findById(commentId);
        if (!comment.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Set<Likes> likes = comment.get().getLikes();
        Likes newLike = new Likes(user);

        boolean isRemove = false;

        if (likes.contains(newLike)) {
            Likes deleteLike = likes.stream().filter(like -> like.equals(newLike)).findFirst().get();
            likes.remove(deleteLike);

            likesRepository.delete(deleteLike);
            isRemove = true;
        }
        else {
            likes.add(newLike);
            comment.get().setLikes(likes);

            commentsRepository.save(comment.get());
        }

        return new ResponseEntity<>(
                new JSONObject()
                        .put("isRemove", isRemove)
                        .put("commentId", commentId)
                        .put("countLikes", likes.size())
                        .toString(),
                HttpStatus.OK
        );
    }

    public HttpStatus addRating(User user, long summaryId, byte ratingNumber) {
        Optional<Summaries> summaries = summariesRepository.findById(summaryId);
        if ((!summaries.isPresent()) || !(ratingNumber >= Rating.minMark && ratingNumber <= Rating.maxMark))
            return HttpStatus.BAD_REQUEST;

        Set<Rating> ratings = summaries.get().getRatings();
        Rating newRating = new Rating(user, ratingNumber);

        boolean isRemove = false;

        if (ratings.contains(newRating)){
            Rating updateRating = ratings.stream().filter(like -> like.equals(newRating)).findFirst().get();
            updateRating.setMark(ratingNumber);
            summariesRepository.save(summaries.get());
        }
        else {
            ratings.add(newRating);
            summaries.get().setRatings(ratings);

            summariesRepository.save(summaries.get());
        }

        simpMessagingTemplate.convertAndSend(
                "/topic/main/updateRating",
                new JSONObject()
                        .put("isRemove", isRemove)
                        .put("summaryId", summaryId)
                        .put("avgRatings", Math.round(
                                ratings.stream()
                                        .mapToDouble(Rating::getMark)
                                        .average()
                                        .orElse(0))
                        )
                        .toString()
        );

        return HttpStatus.OK;
    }
}

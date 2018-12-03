package Com.VSummary.service;

import Com.VSummary.domain.entities.MySQL.*;
import Com.VSummary.repository.CommentsRepository;
import Com.VSummary.repository.LikesRepository;
import Com.VSummary.repository.SummariesRepository;
import Com.VSummary.repository.SummaryTagsRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.jws.WebParam;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    private SummaryTagsRepository summaryTagsRepository;

    public String main(User user, Model model) {
        model.addAttribute("isAuthenticate", !(user == null));

        List<Summaries> summaries = summariesRepository.findAll();

        addTags(summaries, model);
        addRecentSummariesToModel(model, user, summaries, 5);
        addRatedSummariesToModel(model, user, summaries, 5);

        return "main";
    }

    private void addTags(List<Summaries> summaries, Model model){
        Map<SummaryTags, Integer> counterMap = new HashMap<>();

        summaries
            .stream()
            .map(Summaries::getSummaryTags)
            .flatMap((Set<SummaryTags> x) -> Arrays.stream(x.toArray()))
            .forEach(tag -> {
                if (counterMap.containsKey((SummaryTags)tag))
                    counterMap.replace((SummaryTags)tag, counterMap.get(tag) + 1);
                else
                    counterMap.put((SummaryTags)tag, 1);
            });

        model.addAttribute("tags", createTags(counterMap));
    }

    private List<Map<String, Object>> createTags(Map<SummaryTags, Integer> counterMap) {
        Integer max = Collections.max(counterMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getValue();

        List<Map<String, Object>> tags = new ArrayList<>();
        for (Map.Entry<SummaryTags, Integer> entry : counterMap.entrySet())
            tags.add(createTag(entry, max));

        return tags;
    }

    private Map<String, Object> createTag(Map.Entry<SummaryTags, Integer> entry, int max) {
        Map<String, Object> tag = new HashMap<>();

        tag.put("tagName", entry.getKey().getTag());
        tag.put("tagId", entry.getKey().getId());
        tag.put("tagValue", entry.getValue() > 1 ? (max * (entry.getValue() - 1)) / (5 - 1) : 1);

        return tag;
    }

    private void addRecentSummariesToModel(Model model, User user, List<Summaries> summaries, long limit){
        Comparator<Summaries> recentSummariesComparator = Comparator
                .comparingLong(
                        (Summaries summary) -> summary.getTimestamp().getTime()
                )
                .reversed();

        model.addAttribute("recentSummaries", createListSummaries(user, getSortedLimitSummaries(summaries, recentSummariesComparator, limit)));
        model.addAttribute("isRecentFull", ((List<?>)model.asMap().get("recentSummaries")).size() >= limit);
    }

    private void addRatedSummariesToModel(Model model, User user, List<Summaries> summaries, long limit){
        Comparator<Summaries> ratedSummariesComparator = Comparator
                .comparingDouble(
                        (Summaries summary) -> getAvg(summary.getRatings())
                )
                .reversed();

        model.addAttribute("ratedSummaries", createListSummaries(user, getSortedLimitSummaries(summaries, ratedSummariesComparator, limit)));
        model.addAttribute("isRatedFull", ((List<?>)model.asMap().get("ratedSummaries")).size() >= limit);
    }

    private List<Summaries> getSortedLimitSummaries(List<Summaries> summaries, Comparator<Summaries> comparator, long limit){
        return summaries.stream()
            .sorted(comparator)
            .limit(limit)
            .collect(Collectors.toList());
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
        mapSummary.put("username", summary.getUser().getGivenName());
        mapSummary.put("timestamp", getTime(summary.getTimestamp()));
        mapSummary.put("tags", summary.getSummaryTags().stream().map(SummaryTags::getTag).collect(Collectors.toList()));

        if (user != null) {
            mapSummary.put("avgRatings", Math.round(getAvg(summary.getRatings())));
            mapSummary.put("comments", createListComments(summary.getComments(), user));
        }

        return mapSummary;
    }

    private List<Map<String, Object>> createListComments(Set<Comments> comments, User user){
        List<Map<String, Object>> mapComments = new ArrayList<>();

        for (Comments comment : comments) {
            mapComments.add(createMapComment(comment, user));
        }

        mapComments.sort(Comparator
                .comparingLong(
                        (Map<String, Object> comment)-> (Timestamp.valueOf((String)comment.get("timestamp")).getTime())
                )
        );

        return mapComments;
    }

    private Map<String, Object> createMapComment(Comments comment, User user){
        Map<String, Object> mapComment = new HashMap<>();

        mapComment.put("id", comment.getId());
        mapComment.put("userName", comment.getUser().getGivenName());
        mapComment.put("message", comment.getMessage());
        mapComment.put("timestamp", getTime(comment.getTimestamp()));
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
                        .put("timestamp", getTime(newComment.getTimestamp()))
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
                        .put("avgRatings", Math.round(getAvg(ratings)))
                        .toString()
        );

        return HttpStatus.OK;
    }

    private double getAvg(Set<Rating> ratings){
        return ratings.stream()
                .mapToDouble(Rating::getMark)
                .average()
                .orElse(0);
    }

    public String showRated(User user, Model model) {
        model.addAttribute("isAuthenticate", !(user == null));
        List<Summaries> summaries = summariesRepository.findAll();

        addRatedSummariesToModel(model, user, summaries, summaries.size());

        return "main";
    }

    public String showRecent(User user, Model model) {
        model.addAttribute("isAuthenticate", !(user == null));
        List<Summaries> summaries = summariesRepository.findAll();

        addRecentSummariesToModel(model, user, summaries, summaries.size());

        return "main";
    }

    public String showSummary(User user, long summaryId, Model model) {
        Optional<Summaries> summaries = summariesRepository.findById(summaryId);
        if (!summaries.isPresent())
            return "redirect:/login";

        model.addAttribute("isAuthenticate", !(user == null));
        model.addAttribute("summary", createMapSummary(summaries.get(), user));

        return "summary";
    }

    private String getTime(Timestamp timestamp){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    }

    public String showTag(User user, long tagId, Model model) {
        Optional<SummaryTags> tag = summaryTagsRepository.findById(tagId);
        if (!tag.isPresent())
            return "redirect:/login";

        List<Summaries> summaries = summariesRepository.findAllBySummaryTags(tag.get());

        addTags(summaries, model);
        model.addAttribute("isAuthenticate", !(user == null));
        model.addAttribute("summaries", createListSummaries(user, summaries));

        return "main";
    }
}

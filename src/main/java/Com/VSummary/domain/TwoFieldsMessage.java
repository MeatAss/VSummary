package Com.VSummary.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TwoFieldsMessage extends SimpleMessage {
    private String secondMessage;

    public TwoFieldsMessage(String firstMessage, String secondMessage){
        super(firstMessage);
        this.secondMessage = secondMessage;
    }
}

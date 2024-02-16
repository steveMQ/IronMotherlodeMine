package scripts;

import lombok.Data;

@Data
public class MotherlodeMineSettings {

    private boolean largerSackEnabled ;

    public void setLargerSackEnabled(boolean answer){
        largerSackEnabled = answer;
    }

}

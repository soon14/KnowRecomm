import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.k3itech.IRecommApplicationTag;
import com.k3itech.irecomm.re.entity.IreTagWord;
import com.k3itech.irecomm.re.service.IIreTagWordService;
import com.k3itech.service.TagService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;

import java.util.List;

/**
 * @author:dell
 * @since: 2021-05-27
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IRecommApplicationTag.class)
public class BaseTest {
    @Autowired
    private TagService tagService;

    @Autowired
    private IIreTagWordService ireTagWordService;

    @Test
    public void tagService(){

        List<IreTagWord> ireTagWords = ireTagWordService.list();
        for (IreTagWord ireTagWord:ireTagWords) {
            CustomDictionary.add(ireTagWord.getWord());
        }
        CoreStopWordDictionary.add("-");

        tagService.tagKnowledge();
        tagService.tagUsers();
    }


}

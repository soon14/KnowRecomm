import com.k3itech.IRecommApplicationCalc;
import com.k3itech.irecomm.re.entity.IreKnowledgeInfo;
import com.k3itech.irecomm.re.entity.IreUserFollow;
import com.k3itech.irecomm.re.service.IIreKnowledgeInfoService;
import com.k3itech.irecomm.re.service.IIreUserFollowService;
import com.k3itech.service.CalculateService;
import com.k3itech.service.RedisService;
import com.k3itech.utils.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:dell
 * @since: 2021-06-03
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IRecommApplicationCalc.class)
public class CalcTest {

    @Autowired
    CalculateService calculateService;
    @Autowired
    IIreUserFollowService iIreUserFollowService;
    @Autowired
    IIreKnowledgeInfoService iIreKnowledgeInfoService;

    @Resource
    private RedisService redisService;


    @Test
    public void calcTest(){
        String flag = redisService.get(CommonConstants.DEAL_FLAG);
        if (flag.equalsIgnoreCase(CommonConstants.TAG_OVER_FLG)) {
            redisService.set(CommonConstants.DEAL_FLAG, CommonConstants.CALC_ING_FLG);
            List<IreUserFollow> ireUserFollowList = iIreUserFollowService.list();
            List<IreKnowledgeInfo> ireKnowledgeInfos = iIreKnowledgeInfoService.list();
            for (IreUserFollow ireUserFollow : ireUserFollowList) {
                calculateService.compareU2K(ireUserFollow, ireKnowledgeInfos);
            }

            redisService.set(CommonConstants.DEAL_FLAG, CommonConstants.CALC_OVER_FLG);
        }else{
            log.debug("curent deal flag is "+flag);

        }
    }
}

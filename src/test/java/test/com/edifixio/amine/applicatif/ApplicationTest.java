package test.com.edifixio.amine.applicatif;

import java.io.IOException;

import org.junit.Test;

import com.edifixio.amine.application.Application;
import com.edifixio.amine.application.SimpleRootConfig;
import com.edifixio.amine.exception.QuickElasticException;
import com.edifixio.amine.utils.Ressources;
import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;

public class ApplicationTest {
	@Test
	public void test() throws ReflectiveOperationException, QuickElasticException, IOException{
		Application application=new Application(JsonHandleUtil.jsonFile(Ressources.JSON_QUERIES+"query_voiture.json").getAsJsonObject());
		System.out.println( application.getInitialResult(JsonHandleUtil.jsonString("{query:{match_all:{}}}").getAsJsonObject()));
		
	}

}

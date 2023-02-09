package aida.preparation.mentionrecognition;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import aida.data.Mentions;
import aida.preparation.mentionrecognition.ManualFilter;
import aida.util.FileUtils;
import aida.util.Pair;

import org.junit.Test;

public class ManualFilterTest {

	@Test
	public void testManualFilter() throws IOException {
		File orig = new File("testdata/preparedinput/entities.txt");
		String text = FileUtils.getFileContent(orig);
		ManualFilter manualFilte = new ManualFilter();
		Pair<String, Mentions> filteredTextMentions=manualFilte.filter(text);
		int size=filteredTextMentions.second().getMentions().size();
		
		assertEquals(65,size );
	}

}

package aida.data;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import aida.util.FileUtils;

public class PreparedCandidatesTest {

	@Test
	public void LoadWrite() throws Exception {
	    File orig = new File("testdata/preparedinput/preparedinputtest.tsv");
	    String origContent = FileUtils.getFileContent(orig);    
	    PreparedCandidates prep = new PreparedCandidates(orig);
	    File tmpFile = File.createTempFile("test", "tmp");
	    tmpFile.deleteOnExit();
	    prep.writeTo(tmpFile);
	    String tmpContent = FileUtils.getFileContent(tmpFile);
	    assertEquals(origContent, tmpContent);
	}

}

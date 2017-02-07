package a_test.algorithms;

import java.util.ArrayList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PosProcessor.PosProcessor;
import kr.ac.kaist.swrc.jhannanum.share.TagMapper;

public class SimplePOSResult22_mod implements PosProcessor {
	/** the level of analysis */
	final private int TAG_LEVEL = 2;
	
	/** temporary list for new tags */
	private ArrayList<String> tagList = null;
	
	/** temporary list for morpheme tags */
	private ArrayList<String> morphemeList = null;
	
	/**
	 * Constructor.
	 */
	public SimplePOSResult22_mod() {
		tagList = new ArrayList<String>();
		morphemeList = new ArrayList<String>();
	}
	
	/**
	 * It changes the morphological analysis result with 69 KAIST tags to the simplified result with 22 tags.
	 * @param st - the result of morphological analysis where each eojeol has more than analysis result
	 * @return the simplified POS tagging result
	 */
	@Override
	public Sentence doProcess(Sentence st) {
		String prevTag = null;
		boolean changed = false;
		
		Eojeol[] eojeolSet = st.getEojeols();
		
		//어절단위 테그셋 가져오기
		for (int i = 0; i < eojeolSet.length; i++) {
			String[] tags = eojeolSet[i].getTags();
			prevTag = "";
			
			//69pos를 22pos로 바꾼다.
			for (int j = 0; j < tags.length; j++) {
				tags[j] = TagMapper.getKaistTagOnLevel(tags[j], TAG_LEVEL);
				
				prevTag = tags[j];
			}
		}
		st.setEojeols(eojeolSet);
		
		return st;
	}

	@Override
	public void initialize(String baseDir, String configFile) throws Exception {

	}

	@Override
	public void shutdown() {

	}
}

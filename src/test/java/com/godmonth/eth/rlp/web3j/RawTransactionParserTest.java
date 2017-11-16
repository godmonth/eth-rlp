package com.godmonth.eth.rlp.web3j;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.Test;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.protocol.core.methods.request.RawTransaction;

public class RawTransactionParserTest {

	@Test
	public void parse() {
		Pair<RawTransaction, SignatureData> pair = RawTransactionParser.parseFull(
				"0xf8d88dbeaeb1b56c7a86270ab662581d8504e3b29200840bebc20094e6af6f27f32efd9eb6dc015d9c08f745029e82be80b864cee1ac8f000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000140cc9684af605bae10de801218321c1336bb6294600000000000000000000000024a03a3a4bb4a6fec6c8ba79e3c21fc803f29b9da156cef059edaed211e76eda1889a00b284f973e93376fa7b64b2e7dd0191bd367756292256e14590f57f3a9cab454");
		System.out.println(ToStringBuilder.reflectionToString(pair.getLeft(), ToStringStyle.MULTI_LINE_STYLE));
		System.out.println(ToStringBuilder.reflectionToString(pair.getRight(), ToStringStyle.MULTI_LINE_STYLE));
	}
}

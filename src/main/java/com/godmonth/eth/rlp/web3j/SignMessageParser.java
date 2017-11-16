package com.godmonth.eth.rlp.web3j;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.commons.lang3.tuple.Pair;
import org.web3j.crypto.Sign;
import org.web3j.protocol.core.methods.request.RawTransaction;
import org.web3j.utils.Numeric;

import com.godmonth.eth.rlp.RLPElement;
import com.godmonth.eth.rlp.RLPItem;
import com.godmonth.eth.rlp.RLPList;
import com.godmonth.eth.rlp.RlpDecoder;

public class SignMessageParser {
	private SignMessageParser() {
	}

	public static RawTransaction parseRawTransaction(RLPElement rlpElement) {
		RLPList rlpList = (RLPList) rlpElement;
		List<RLPElement> elements = rlpList.getElements();
		BigInteger nonce = null;
		{
			RLPItem rlpItem = (RLPItem) elements.get(0);
			byte[] rlpData = rlpItem.getRLPData();
			if (ArrayUtils.isNotEmpty(rlpData)) {
				nonce = new BigInteger(rlpData);
			} else {
				nonce = BigInteger.ZERO;
			}
		}
		BigInteger gasPrice = null;
		{
			RLPItem rlpItem = (RLPItem) elements.get(1);
			byte[] rlpData = rlpItem.getRLPData();
			if (ArrayUtils.isNotEmpty(rlpData)) {
				gasPrice = new BigInteger(rlpData);
			} else {
				gasPrice = BigInteger.ZERO;
			}
		}
		BigInteger gasLimit = null;
		{
			RLPItem rlpItem = (RLPItem) elements.get(2);
			byte[] rlpData = rlpItem.getRLPData();
			if (ArrayUtils.isNotEmpty(rlpData)) {
				gasLimit = new BigInteger(rlpData);
			} else {
				gasLimit = BigInteger.ZERO;
			}
		}
		String to = null;
		{
			RLPItem rlpItem = (RLPItem) elements.get(3);
			byte[] rlpData = rlpItem.getRLPData();
			to = "0x" + Hex.encodeHexString(rlpData);
		}
		BigInteger value = null;
		{
			RLPItem rlpItem = (RLPItem) elements.get(4);
			byte[] rlpData = rlpItem.getRLPData();
			if (ArrayUtils.isNotEmpty(rlpData)) {
				value = new BigInteger(rlpData);
			} else {
				value = BigInteger.ZERO;
			}
		}
		String data = null;
		{
			RLPItem rlpItem = (RLPItem) elements.get(5);
			byte[] rlpData = rlpItem.getRLPData();
			data = "0x" + Hex.encodeHexString(rlpData);
		}

		return RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, value, data);
	}

	public static Sign.SignatureData parseSignatureData(RLPElement rlpElement) {
		RLPList rlpList = (RLPList) rlpElement;
		List<RLPElement> elements = rlpList.getElements();
		byte v = 0;
		{
			RLPItem rlpItem = (RLPItem) elements.get(6);
			byte[] rlpData = rlpItem.getRLPData();
			if (ArrayUtils.isNotEmpty(rlpData)) {
				v = rlpData[0];
			}
		}
		byte[] r = null;
		{
			RLPItem rlpItem = (RLPItem) elements.get(7);
			byte[] rlpData = rlpItem.getRLPData();
			if (ArrayUtils.isNotEmpty(rlpData)) {
				r = rlpData;
			} else {
				r = new byte[0];
			}
		}
		byte[] s = null;
		{
			RLPItem rlpItem = (RLPItem) elements.get(8);
			byte[] rlpData = rlpItem.getRLPData();
			if (ArrayUtils.isNotEmpty(rlpData)) {
				s = rlpData;
			} else {
				s = new byte[0];
			}
		}

		return new Sign.SignatureData(v, r, s);
	}

	public static Pair<RawTransaction, Sign.SignatureData> parseFull(RLPElement rlpElement) {
		RawTransaction rawTransaction = parseRawTransaction(rlpElement);
		Sign.SignatureData signatureData = parseSignatureData(rlpElement);
		return Pair.of(rawTransaction, signatureData);
	}

	public static Pair<RawTransaction, Sign.SignatureData> parseFull(String signMessageHex) {
		String input = Numeric.cleanHexPrefix(signMessageHex);
		byte[] decodeHex;
		try {
			decodeHex = Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e1) {
			throw new ContextedRuntimeException(e1);
		}
		RLPElement decode = RlpDecoder.decode(decodeHex);
		return parseFull(decode);
	}
}

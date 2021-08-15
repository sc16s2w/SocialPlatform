package com.tensquare.encrypt.rsa;

/**
 * rsa加解密用的公钥和私钥
 * @author Administrator
 *
 */
public class RsaKeys {

	//生成秘钥对的方法可以参考这篇帖子
	//https://www.cnblogs.com/yucy/p/8962823.html

	//服务器公钥
	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6Dw9nwjBmDD/Ca1QnRGy"
											 + "GjtLbF4CX2EGGS7iqwPToV2UUtTDDemq69P8E+WJ4n5W7Iln3pgK+32y19B4oT5q"
											 + "iUwXbbEaAXPPZFmT5svPH6XxiQgsiaeZtwQjY61qDga6UH2mYGp0GbrP3i9TjPNt"
											 + "IeSwUSaH2YZfwNgFWqj+y/0jjl8DUsN2tIFVSNpNTZNQ/VX4Dln0Z5DBPK1mSskd"
											 + "N6uPUj9Ga/IKnwUIv+wL1VWjLNlUjcEHsUE+YE2FN03VnWDJ/VHs7UdHha4d/nUH"
											 + "rZrJsKkauqnwJsYbijQU+a0HubwXB7BYMlKovikwNpdMS3+lBzjS5KIu6mRv1GoE"
											 + "vQIDAQAB";

	//服务器私钥(经过pkcs8格式处理)
	private static final String serverPrvKeyPkcs8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDoPD2fCMGYMP8J"
				 								 + "rVCdEbIaO0tsXgJfYQYZLuKrA9OhXZRS1MMN6arr0/wT5YniflbsiWfemAr7fbLX"
				 								 + "0HihPmqJTBdtsRoBc89kWZPmy88fpfGJCCyJp5m3BCNjrWoOBrpQfaZganQZus/e"
				 								 + "L1OM820h5LBRJofZhl/A2AVaqP7L/SOOXwNSw3a0gVVI2k1Nk1D9VfgOWfRnkME8"
				 								 + "rWZKyR03q49SP0Zr8gqfBQi/7AvVVaMs2VSNwQexQT5gTYU3TdWdYMn9UeztR0eF"
				 								 + "rh3+dQetmsmwqRq6qfAmxhuKNBT5rQe5vBcHsFgyUqi+KTA2l0xLf6UHONLkoi7q"
				 								 + "ZG/UagS9AgMBAAECggEBANP72QvIBF8Vqld8+q7FLlu/cDN1BJlniReHsqQEFDOh"
				 								 + "pfiN+ZZDix9FGz5WMiyqwlGbg1KuWqgBrzRMOTCGNt0oteIM3P4iZlblZZoww9nR"
				 								 + "sc4xxeXJNQjYIC2mZ75x6bP7Xdl4ko3B9miLrqpksWNUypTopOysOc9f4FNHG326"
				 								 + "0EMazVaXRCAIapTlcUpcwuRB1HT4N6iKL5Mzk3bzafLxfxbGCgTYiRQNeRyhXOnD"
				 								 + "eJox64b5QkFjKn2G66B5RFZIQ+V+rOGsQElAMbW95jl0VoxUs6p5aNEe6jTgRzAT"
				 								 + "kqM2v8As0GWi6yogQlsnR0WBn1ztggXTghQs2iDZ0YkCgYEA/LzC5Q8T15K2bM/N"
				 								 + "K3ghIDBclB++Lw/xK1eONTXN+pBBqVQETtF3wxy6PiLV6PpJT/JIP27Q9VbtM9UF"
				 								 + "3lepW6Z03VLqEVZo0fdVVyp8oHqv3I8Vo4JFPBDVxFiezygca/drtGMoce0wLWqu"
				 								 + "bXlUmQlj+PTbXJMz4VTXuPl1cesCgYEA6zu5k1DsfPolcr3y7K9XpzkwBrT/L7LE"
				 								 + "EiUGYIvgAkiIta2NDO/BIPdsq6OfkMdycAwkWFiGrJ7/VgU+hffIZwjZesr4HQuC"
				 								 + "0APsqtUrk2yx+f33ZbrS39gcm/STDkVepeo1dsk2DMp7iCaxttYtMuqz3BNEwfRS"
				 								 + "kIyKujP5kfcCgYEA1N2vUPm3/pNFLrR+26PcUp4o+2EY785/k7+0uMBOckFZ7GIl"
				 								 + "FrV6J01k17zDaeyUHs+zZinRuTGzqzo6LSCsNdMnDtos5tleg6nLqRTRzuBGin/A"
				 								 + "++xWn9aWFT+G0ne4KH9FqbLyd7IMJ9R4gR/1zseH+kFRGNGqmpi48MS61G0CgYBc"
				 								 + "PBniwotH4cmHOSWkWohTAGBtcNDSghTRTIU4m//kxU4ddoRk+ylN5NZOYqTxXtLn"
				 								 + "Tkt9/JAp5VoW/41peCOzCsxDkoxAzz+mkrNctKMWdjs+268Cy4Nd09475GU45khb"
				 								 + "Y/88qV6xGz/evdVW7JniahbGByQhrMwm84R9yF1mNwKBgCIJZOFp9xV2997IY83S"
				 								 + "habB/YSFbfZyojV+VFBRl4uc6OCpXdtSYzmsaRcMjN6Ikn7Mb9zgRHR8mPmtbVfj"
				 								 + "B8W6V1H2KOPfn/LAM7Z0qw0MW4jimBhfhn4HY30AQ6GeImb2OqOuh3RBbeuuD+7m"
				 								 + "LpFZC9zGggf9RK3PfqKeq30q";

//	//服务器公钥
//	private static final String serverPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6RJY4fO1cHYYQ5r81wcpXOknv" +
//			"ktoK9s/N58ge9U305ZdpMNjKFwy1efv2DPKIULPby8MdF4zdnPensZrpSmFIrZod" +
//			"yT98l/usxcCJcmhoEslvmqQAPmQW6XsaZb6/rqo3qWICWmauz7gNbRdl/7J5w6F4" +
//			"uzVqQ9wFWeZ29Bi/tQIDAQAB";
//
//	//服务器私钥(经过pkcs8格式处理)
//	private static final String serverPrvKeyPkcs8 = "MIICXgIBAAKBgQC6RJY4fO1cHYYQ5r81wcpXOknvktoK9s/N58ge9U305ZdpMNjK\n" +
//			"Fwy1efv2DPKIULPby8MdF4zdnPensZrpSmFIrZodyT98l/usxcCJcmhoEslvmqQA" +
//			"PmQW6XsaZb6/rqo3qWICWmauz7gNbRdl/7J5w6F4uzVqQ9wFWeZ29Bi/tQIDAQAB" +
//			"AoGAIwYwhPCgpRh8FAN/YrlMtbglHOItE7CzBFGX5JJVlT3yiXoA2YcaAamtQefP" +
//			"Vuht2V9oEblS6HzSaHrR2Rn+Tpy0jt1HRw5N6rkboMJoMPT3w+cEdl5Fp+pph0c0" +
//			"OU/Shnz2sLU0zDOqJvfJwLpeyveiujLbtF2SrPUpJz7pgqUCQQDvlQ3tHu5Og4rJ" +
//			"W1AU7NBMQcppe/zgCLkeOjfeZw/+dS1sMO1IsiRbzYOWILkdeqqi2uzsacAbp6s1" +
//			"g1H9u4F3AkEAxwg/1K/j/TU39udbL02HbEWbXs9x5IQUcGDHlxQzeoe2903B6Y8e" +
//			"Owd2tTttVTvVD39wijjZWaVqNDcC+XbzMwJBAM4agTd7A3FhsjRUrIjDzNG9S18B" +
//			"feJRPsdlOAjIyraRoXjQgmCmd6bOHqQ5FGbqzv54rZcXxl9EF4JarV9FPjMCQQDB" +
//			"JYlELljyzWV9DrGXqwYjlT0c+fVNzLtE/zZRr8HxQ6jUP7pRnJi6shiJ8Zy9XS37" +
//			"NzIQgPC0JWl2OHiDfOKvAkEA5g2PREmJQGAHchD966rkpt9ij/7ydnqxaj3foeAg" +
//			"MMi1E+8pW/IXoHAkRSVfyzJgqAc1pLmfydzeZYWF014NDQ==";

	public static String getServerPubKey() {
		return serverPubKey;
	}

	public static String getServerPrvKeyPkcs8() {
		return serverPrvKeyPkcs8;
	}
	
}

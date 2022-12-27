package co.syscoop.soberano.obf;

import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.salt.RandomSaltGenerator;

public class PropObf extends SimpleStringPBEConfig {

	public PropObf() {
		
		super();
		super.setAlgorithm("PBEWithMD5AndDES");		
		String obfPassword = System.getenv("SOB_OBF");
		
		//TODO: change Soberano instance default the password
		super.setPassword(obfPassword != null ? obfPassword : "Ucymp_91G6*=R");
		RandomSaltGenerator saltGenerator = new RandomSaltGenerator();
		super.setSaltGenerator(saltGenerator);		
	}
}

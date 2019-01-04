package com.proaktivio.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="proaktivio")
public class ProaktivioProperties {
	private String hostAddress;
	private String signupAddress;
	private String signinAddress;
	public ProaktivioProperties() {
		super();
	}
	public String getHostAddress() {
		return hostAddress;
	}
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}
	public String getSignupAddress() {
		return signupAddress;
	}
	public void setSignupAddress(String signupAddress) {
		this.signupAddress = signupAddress;
	}
	public String getSigninAddress() {
		return signinAddress;
	}
	public void setSigninAddress(String signinAddress) {
		this.signinAddress = signinAddress;
	}
}

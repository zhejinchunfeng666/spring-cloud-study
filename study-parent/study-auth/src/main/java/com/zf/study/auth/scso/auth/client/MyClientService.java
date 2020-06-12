package com.zf.study.auth.scso.auth.client;

import com.zf.study.core.dto.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@Service("myClientService")
@Slf4j
public class MyClientService implements ClientDetailsService{

private ClientDetailsService clientDetailsService;
	

	@Autowired
	private StudyClientProperty studyClientProperty;

	@PostConstruct
	public void init() {
		log.debug("=========MyClientDetailsService[init]::Spring Security Oauth2 Support Client Info START=========");
		// 配置的客户端信息
		List<StudyClient> clients = studyClientProperty.getClients();
		log.debug("MyClientDetailsService[init]::clients=" + clients.toString());
		if (clients != null && clients.size() > 0) {

			
			MyInMemoryClientServiceBuilder inMemoryClientDetailsServiceBuilder = new MyInMemoryClientServiceBuilder();
			// 循环构造BaseClientDetails对象，并放入
			for (StudyClient clubClientDetail : clients) {
				// 验证配置是否正确，不正确则不构造BaseClientDetails
				if (!clubClientDetail.validate()) {
					continue;
				}

				// 实例化BaseClientDetails对象
				BaseClientDetails baseClientDetails = new BaseClientDetails();
				baseClientDetails.setClientId(clubClientDetail.getClientId());
				baseClientDetails.setClientSecret(encodeAppSecret(clubClientDetail.getClientSecret()));
				baseClientDetails.setResourceIds(new LinkedHashSet<String>(clubClientDetail.getResourceIds()));
				baseClientDetails
						.setAuthorizedGrantTypes(new LinkedHashSet<String>(clubClientDetail.getAuthorizedGrantTypes()));
				baseClientDetails.setScope(new LinkedHashSet<String>(clubClientDetail.getScope()));

				List<String> authorities = clubClientDetail.getAuthorities();
				Set<SimpleGrantedAuthority> authoritieSet = new LinkedHashSet<SimpleGrantedAuthority>();
				for (String authority : authorities) {
					SimpleGrantedAuthority sga = null;
					if (authority != null && !"".equals(authority.trim())) {
						sga = new SimpleGrantedAuthority(authority.trim());
						authoritieSet.add(sga);
					}
				}
				if (authoritieSet.size() > 0) {
					baseClientDetails.setAuthorities(authoritieSet);
				}

				// 将BaseClientDetails对象放入ClientDetailsService创建器
				inMemoryClientDetailsServiceBuilder.addClient(clubClientDetail.getClientId(), baseClientDetails);
			}

			try {
				clientDetailsService = inMemoryClientDetailsServiceBuilder.build();
			} catch (Exception e) {
				e.printStackTrace();
				log.debug("MyClientDetailsService[init]::Exception=" + e.getMessage());
			}
			log.debug("=========MyClientDetailsService[init]::Spring Security Oauth2 Support Client Info END=========");
		}
	}

	/**
	 * Convert ClubClientDetail to ClubClient
	 * 
	 * @param clients
	 * @return
	 */
	private List<Client> convertToClubClient(List<StudyClient> clients) {
		return clients.stream().map(ccd -> {
			Client cc = new Client();
			cc.setClient_id(ccd.getClientId());
			cc.setClient_name(ccd.getClientName());
			return cc;
		}).collect(Collectors.toList());
	}

	/**
	 * 加密appSecret
	 *
	 * @param appSecret
	 * @return
	 */
	private String encodeAppSecret(String appSecret) {
		return "{bcrypt}" + new BCryptPasswordEncoder().encode(appSecret);
	}

	/**
	 * loadClientByClientId
	 *
	 * @param clientId
	 * @return
	 */
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return clientDetailsService.loadClientByClientId(clientId);
	}
}

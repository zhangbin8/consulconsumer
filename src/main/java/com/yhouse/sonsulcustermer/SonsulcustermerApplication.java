package com.yhouse.sonsulcustermer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class SonsulcustermerApplication {


	@Autowired
	private RestTemplate restTemplate;

	@Bean
	@LoadBalanced //开启负债均衡的能力
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired(required=true)
	private LoadBalancerClient loadBalancer;
	/**
	 * 从所有服务中选择一个服务（轮询）
	 */
	@RequestMapping("/discover")
	public Object discover() {
		return loadBalancer.choose("jishi2").getUri().toString();
	}

	/**
	 * 获取所有服务
	 */
	@RequestMapping("/services")
	public Object services() {
		return discoveryClient.getInstances("jishi2");
	}

	@RequestMapping(value = "/hi")
	public String add() {
		return restTemplate.getForEntity("http://serviceName-xijiaocheng/hi", String.class).getBody();
	}

	public static void main(String[] args) {
		SpringApplication.run(SonsulcustermerApplication.class, args);
	}
}

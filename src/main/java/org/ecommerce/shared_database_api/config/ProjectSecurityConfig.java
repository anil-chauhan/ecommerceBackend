package org.ecommerce.shared_database_api.config;


import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.shared_database_api.exceptionhandling.CustomAccessDeniedHandler;
import org.ecommerce.shared_database_api.filter.CsrfCookieFilter;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Configuration
//@Profile("!prod")
public class ProjectSecurityConfig {

    /*@Value("${spring.security.oauth2.resourceserver.opaque.introspection-uri}")
    String introspectionUri;

    @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-id}")
    String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-secret}")
    String clientSecret;*/






        @Bean
        SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
            JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
            jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
            CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
            http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                            CorsConfiguration config = new CorsConfiguration();
                            ArrayList<String> origins=new ArrayList<>();
                            origins.add("http://localhost:4200");
                            origins.add("http://192.168.29.198:4200");
                            origins.add("http://192.168.29.198:5005");
                            origins.add("http://192.168.29.198:8090");
                            origins.add("http://localhost:5005");

                            config.setAllowedOrigins(origins);


                            //config.setAllowedOrigins(Collections.singletonList("http://192.168.29.198:4200"));
                            //config.setAllowedOrigins(Collections.singletonList("http://192.168.29.198:5005"));
                            //config.setAllowedOrigins(Collections.singletonList("http://localhost:5005"));

                            config.setAllowedMethods(Collections.singletonList("*"));
                            config.setAllowCredentials(true);
                            config.setAllowedHeaders(Collections.singletonList("*"));
                            config.setExposedHeaders(Arrays.asList("Authorization"));
                            config.setMaxAge(3600L);
                            return config;
                        }
                    }))

                    // for remove endpoint from security check, then add url in ignoringRequestMatchers, and
                    //requestMatchers.("/endpoint").permitAll()
                    // and remove from .requestMatchers("/create_category").hasRole("ADMIN")

                    .csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                            .ignoringRequestMatchers("/contact", "/register","/create_category",
                                    "/add_product","/get_all_category","/get_all_product_from_a_category_by_name","/is_sub_category_available"
                            ,"/create_category_by_name","/get_all_product_by_name","/findByCountryCode",
                                    "/countries","/api/checkout/purchase","/get_all_product","/hello",
                                    "/get_all_category_details" ,"/get_all_trendy_product","/create_order",
                                    "/update_payment_status","/find_orders_by_email","/update_pending_payment","/get_order_items_by_order_id",
                                    "/get_all_product_by_category_id","/api/checkout/get_address_by_customer_email","/get_product_by_id")
                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                    .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                    .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure()) // Only HTTP
                    .authorizeHttpRequests((requests) -> requests
                            .requestMatchers("/create_new_user").hasRole("USER")
                            //.requestMatchers("/create_category").hasRole("ADMIN")
                            //.requestMatchers("/get_product_by_id").hasRole("USER")
                            .requestMatchers("/myAccount").hasRole("USER")
                            .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
                            .requestMatchers("/myLoans").authenticated()
                            .requestMatchers("/myCards").hasRole("USER")
                            .requestMatchers("/user").authenticated()
                            .requestMatchers("/notices", "/contact", "/error", "/create_category","/add_product",
                                    "/get_all_category","/get_all_product_from_a_category_by_name","/is_sub_category_available",
                                    "/create_category_by_name","/get_all_product_by_name",
                                    "/findByCountryCode","/countries","/api/checkout/purchase",
                                    "get_all_product","/hello","/get_all_category_details" ,
                                    "/get_all_trendy_product","create_order","/update_payment_status",
                                    "/find_orders_by_email","/update_pending_payment","/get_order_items_by_order_id",
                                    "/get_all_product_by_category_id","/api/checkout/get_address_by_customer_email",
                                    "/get_product_by_id").permitAll());
            http.oauth2ResourceServer(rsc -> rsc.jwt(jwtConfigurer ->
                    jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)));
            /*http.oauth2ResourceServer(rsc -> rsc.opaqueToken(otc -> otc.authenticationConverter(new KeycloakOpaqueRoleConverter())
                    .introspectionUri(this.introspectionUri).introspectionClientCredentials(this.clientId,this.clientSecret)));*/
            http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
            return http.build();
    }

}

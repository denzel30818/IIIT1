package tw.t1.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import tw.t1.inteceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 註冊 Interceptor
		InterceptorRegistration ir = registry.addInterceptor(new LoginInterceptor());
		// 設定要攔截的路徑
		ir.addPathPatterns("/**");
		// 設定不攔截的路徑
		ir.excludePathPatterns("/", "/login", "/css/**", "/fonts/**", "/images/**", "/js/**", "/favicon.ico",
				"/register", "/index", "/logout", "/error", "/img/**", "/forgetPassword", "/active", "/registerSuccess",
				"/activeSuccess", "/activeFail", "/shop/**", "/forum/**", "/news/list", "/news/searchByMonth",
				"/news/showNews", "/article/list", "/article/get", "/article/announcementGet", "/member/feedback",
				"/member/feedback/success", "/member/about", "/article/forumArticleSearch", "/article/typeSearch");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		exposeDirectory("src/main/resources/static/images/products", registry);
	}

	private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
		Path uploadDir = Paths.get(dirName);
		String uploadPath = uploadDir.toFile().getAbsolutePath();

		if (dirName.startsWith("../"))
			dirName = dirName.replace("../", "");

		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + uploadPath + "/");
	}

}

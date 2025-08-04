package com.MyBlackBook.datingbook;

import com.MyBlackBook.datingbook.model.Tag;
import com.MyBlackBook.datingbook.repository.TagRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.MyBlackBook.datingbook.model.Person;
import com.MyBlackBook.datingbook.model.Child;
import com.MyBlackBook.datingbook.repository.PersonRepository;
import com.MyBlackBook.datingbook.repository.ChildRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.time.LocalDate;
import java.util.List;


@SpringBootApplication
public class DatingBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatingBookApplication.class, args);
	}

	@Bean
	public CommandLineRunner testData(PersonRepository personRepository, ChildRepository childRepository, TagRepository tagRepository) {
		return args -> {
			// Person 1
			Person alice = new Person("Alice", LocalDate.parse("1978-05-12"), 46, "Engineer", "Austin");
			personRepository.save(alice);

			Child aliceChild1 = new Child("Emily", LocalDate.parse("2010-03-15"), 14, "Mom Mon-Wed", alice);
			Child aliceChild2 = new Child("Jack", LocalDate.parse("2012-09-22"), 12, "Mom Thurs-Fri", alice);
			childRepository.saveAll(List.of(aliceChild1, aliceChild2));

			// Person 2
			Person bob = new Person("Bob", LocalDate.parse("1985-11-30"), 38, "Chef", "Dallas");
			personRepository.save(bob);

			Child bobChild = new Child("Sophie", LocalDate.parse("2014-07-10"), 10, "Dad weekends", bob);
			childRepository.save(bobChild);

			// Person 3
			Person claire = new Person("Claire", LocalDate.parse("1990-08-20"), 34, "Designer", "Houston");
			personRepository.save(claire);
			// No kids for Claire

			// Tag 1
			Tag karaoke = new Tag("Likes Karaoke");
			tagRepository.save(karaoke);

			// Tag 2
			Tag singleMom = new Tag("Single Mom");
			tagRepository.save(singleMom);
		};
	}
	@Bean
	public ApplicationRunner printRoutes(RequestMappingHandlerMapping mapping) {
		return args -> mapping.getHandlerMethods()
				.forEach((key, value) -> System.out.println(key + " => " + value));
	}

}


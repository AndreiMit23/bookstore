package com.example.bookstore.mapper;

import com.example.bookstore.dto.module_publisher.PublisherResponseBuilder;
import com.example.bookstore.entity.Publisher;
import com.example.bookstore.dto.module_publisher.PublisherRequest;
import com.example.bookstore.dto.module_publisher.PublisherResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PublisherMapper {
    private final BookMapper bookMapper;

    public PublisherMapper(BookMapper bookMapper){
        this.bookMapper = bookMapper;
    }

    public Publisher toEntity(PublisherRequest publisherRequest){
        return new Publisher(publisherRequest.getName(), publisherRequest.getCity());
    }

    public PublisherResponse toResponse(Publisher publisher){
        PublisherResponse publisherResponse = PublisherResponseBuilder.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .city(publisher.getCity())
                .build();

        publisherResponse.setBookResponseList(bookMapper.toResponseList(publisher.getBookList()));

        return publisherResponse;
    }

    public List<PublisherResponse> publisherResponseList(List<Publisher> publishers){
        List<PublisherResponse> publisherResponseList = new ArrayList<>();

        for(Publisher publisher : publishers){
            publisherResponseList.add(toResponse(publisher));
        }

        return publisherResponseList;
    }
}

package com.BaGulBaGul.BaGulBaGul.domain.recruitment.dto;

import com.BaGulBaGul.BaGulBaGul.domain.post.dto.PostConditionalRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class RecruitmentConditionalRequest {
    private String title;
    private List<String> tags;
    private String username;
    private Long eventId;

    public PostConditionalRequest toPostConditionalRequest() {
        return PostConditionalRequest.builder()
                .title(title)
                .username(username)
                .tags(tags)
                .build();
    }
}

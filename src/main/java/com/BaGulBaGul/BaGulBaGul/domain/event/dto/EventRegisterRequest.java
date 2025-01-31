package com.BaGulBaGul.BaGulBaGul.domain.event.dto;

import com.BaGulBaGul.BaGulBaGul.domain.event.constant.EventType;
import com.BaGulBaGul.BaGulBaGul.domain.post.dto.api.request.PostRegisterRequest;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRegisterRequest {

    @ApiModelProperty(value = "이벤트 타입 FESTIVAL, LOCAL_EVENT, PARTY 중 하나 | 필수")
    @NotNull(message = "이벤트 종류는 null일 수 없습니다.")
    private EventType type;

    @ApiModelProperty(value = "이벤트 제목 | 필수, 공백 불허")
    @NotBlank(message = "제목은 null이거나 빈 문자열일 수 없습니다.")
    private String title;

    @ApiModelProperty(value = "모집 인원")
    @Min(value = 1, message = "모집 인원은 {1}명 이상이여야 합니다")
    private Integer maxHeadCount;

    @ApiModelProperty(value = "세부 주소", example = "서울시 영등포구 xxx로 xxx타워 x층")
    private String fullLocation;

    @ApiModelProperty(value = "요약 주소", example = "서울시 영등포구")
    private String abstractLocation;

    @ApiModelProperty(value = "위도")
    private Float latitudeLocation;

    @ApiModelProperty(value = "경도")
    private Float longitudeLocation;

    @ApiModelProperty(value = "게시글 내용")
    private String content;

    @ApiModelProperty(value = "연령 제한 게시물 여부")
    private Boolean ageLimit;

    @ApiModelProperty(value = "시작 시간")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "종료 시간")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    @ApiModelProperty(value = "태그들", example = "[\"물놀이\",\"바베큐\"]")
    @Size(max = 10, message = "태그 개수는 {1}개 이하여야 합니다.")
    private List<String> tags;

    @ApiModelProperty(value = "등록할 카테고리의 이름들", example = "[\"스포츠/레저\",\"식품/음료\",\"문화/예술\"]")
    @Size(max = 2, message = "카테고리 개수는 {1}개 이하여야 합니다.")
    private List<String> categories;

    @ApiModelProperty(value = "등록한 이미지들의 resource id. 순서는 보존되며 첫번째 이미지가 대표이미지가 된다.")
    @Size(max = 10, message = "이미지 개수는 {1}개 이하여야 합니다.")
    private List<Long> imageIds;

    @AssertTrue(message = "이벤트 시작 날짜는 이벤트 종료 날짜보다 빨라야 합니다.")
    public boolean isStartDateBeforeEndDate() {
        return startDate == null || endDate == null || startDate.isBefore(endDate);
    }

    public PostRegisterRequest toPostRegisterRequest() {
        return PostRegisterRequest.builder()
                .title(title)
                .content(content)
                .tags(tags)
                .imageIds(imageIds)
                .build();
    }
}

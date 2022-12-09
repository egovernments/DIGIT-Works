package org.egov.web.models;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AttendanceTime {

    private int entryHour;
    private int exitHourHalfDay;
    private int exitHourFullDay;

}

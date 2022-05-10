package by.university.hippo.DTO;

import by.university.hippo.entity.enums.Place;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
    private Long id;
    private PriceListDTO priceList;
    private String date;
    private String time;
    private Place place;
    private List<HorseDTO> horses;
    private List<StaffDTO> staff;
    private String enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceDTO that = (ServiceDTO) o;
        return Objects.equals(priceList, that.priceList) && Objects.equals(date, that.date) && Objects.equals(time, that.time) && place == that.place && Objects.equals(horses, that.horses) && Objects.equals(staff, that.staff) && Objects.equals(enabled, that.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priceList, date, time, place, horses, staff, enabled);
    }
}

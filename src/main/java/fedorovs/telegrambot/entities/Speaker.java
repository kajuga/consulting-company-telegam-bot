package fedorovs.telegrambot.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema = "tbot", name = "speakers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Speaker {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "speakers_id_seq")
    @SequenceGenerator(name = "speakers_id_seq", sequenceName = "speakers_id_seq", schema = "tbot", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "company")
    private String company;

    @Column(name = "description")
    private String description;

}

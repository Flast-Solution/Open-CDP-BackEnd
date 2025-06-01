package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serial;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter  @Setter
@NoArgsConstructor
@Entity
@Table(name = "config", uniqueConstraints = @UniqueConstraint(columnNames = "key"))
public class Config implements java.io.Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "`key`", unique = true, nullable = false, length = 45)
	private String key;

	@Column(name = "`value`", length = 65535)
	private String value;
}

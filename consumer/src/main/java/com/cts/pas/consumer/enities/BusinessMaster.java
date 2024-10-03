package com.cts.pas.consumer.enities;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BusinessMaster {
	@Id
	private int id;
	private int minPercent;
	private int maxPercent;
	private int index;
}
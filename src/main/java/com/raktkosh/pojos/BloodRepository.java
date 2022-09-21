package com.raktkosh.pojos;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.raktkosh.dto.BloodBankRepositoryDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "blood_repository")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BloodRepository {
  
  @EmbeddedId
  private BloodRepositoryID id;
    
  @Column(name = "availability")
  private int availability;
  
  public static BloodRepository build(BloodBankRepositoryDTO repo) {
    BloodBank bank = new BloodBank();
    bank.setId(repo.getBankId());
    return new BloodRepository(new BloodRepositoryID(repo.getType(), repo.getAntigen(), bank), repo.getAvailability());
  }
}

package com.accenture.part2.DTOs;

import com.accenture.part2.models.Doctor;
import com.accenture.part2.models.Insured;

    public class VaccinationDTO {

        private String amka;
        private String vaccinationDate;
        private String vaccinationTime;
        private String expirationDate;
        private String code;
        public VaccinationDTO(String amka, String vaccinationDate,String vaccinationTime, String expirationDate,String code) {
            this.amka = amka;
            this.vaccinationDate = vaccinationDate;
            this.vaccinationTime = vaccinationTime;
            this.expirationDate = expirationDate;
            this.code = code;
        }

        public String getAmka() {
            return amka;
        }

        public void setAmka(String amka) {
            this.amka = amka;
        }

        public String getVaccinationDate() {
            return vaccinationDate;
        }

        public void setVaccinationDate(String vaccinationDate) {
            this.vaccinationDate = vaccinationDate;
        }

        public String getVaccinationTime() {
            return vaccinationTime;
        }

        public void setVaccinationTime(String vaccinationTime) {
            this.vaccinationTime = vaccinationTime;
        }

        public String getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }


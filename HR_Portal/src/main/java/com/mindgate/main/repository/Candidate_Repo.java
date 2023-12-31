package com.mindgate.main.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mindgate.main.domain.Candidate;
@Repository
public class Candidate_Repo implements Candidate_repo_Interface {
	
	
	   @Autowired
      private JdbcTemplate jdbcTemplate;
	   
	   
	    private final static String Insert_New_Candidate="insert into candidate_details values(candidate_sequence.nextVal,?,?,?,?,?,?,?,?,?,?,?)";
	    private final static String Update_existing_Candidate="update candidate_details set candidate_name=?,skills=?,qualification=?,passed_out_year=?,interview_date=?,experience=?,grade=?,phone=?,email=?,apply_date=?,job_id=? where candidate_id=?";
	    private final static String Delete_existing_Candidate="delete from  candidate_details where candidate_id =?";
	    private final static String Select_all_Candidate="select * from candidate_details";
	    private final static String Select_one_Candidate="select * from candidate_details where candidate_id =?";
	@Override
	public boolean addNewCandidate(Candidate candidate) {
		// TODO Auto-generated method stub
		
		Object[] parameters={candidate.getCandidateId(),candidate.getCandidateName(),candidate.getSkills(),candidate.getQualification(),candidate.getPassedOutYear(),candidate.getInterviewDate(),candidate.getExperience(),candidate.getGrade(),candidate.getPhone(),candidate.getEmail(),candidate.getApplyDate(),candidate.getJobId()};
        jdbcTemplate.update(Insert_New_Candidate, parameters);
        return true;
		
	}

	@Override
	public Candidate UpdateCandidate(Candidate candidate) {
		 Object[] parameters={candidate.getCandidateName(),candidate.getSkills(),candidate.getQualification(),candidate.getPassedOutYear(),candidate.getInterviewDate(),candidate.getExperience(),candidate.getGrade(),candidate.getPhone(),candidate.getEmail(),candidate.getApplyDate(),candidate.getJobId(),candidate.getCandidateId()};
	        int rowcount=jdbcTemplate.update(Update_existing_Candidate,parameters);
	        if(rowcount>0){
	            return getCandidateByCandidateId(candidate.getCandidateId());
	        }
	        return null;
	    }
	

	@Override
	public boolean deleteCandidate(String candidateId) {
		 int rowcount=jdbcTemplate.update(Delete_existing_Candidate,candidateId);
	        if(rowcount>0){
	        return true;
	        }
	        else{
	            return false;
	        }
	}

	@Override
	public Candidate getCandidateByCandidateId(String candidateId) {
		CandidateRowMapper candidateRowMapper=new CandidateRowMapper();
	      Candidate candidate=jdbcTemplate.queryForObject(Select_one_Candidate, candidateRowMapper,candidateId);
	        return  candidate;
	}

	@Override
	public List<Candidate> getAllCandidate() {
		// TODO Auto-generated method stub
		CandidateRowMapper candidateRowMapper=new CandidateRowMapper();
		return jdbcTemplate.query(Select_all_Candidate, candidateRowMapper);
	}

}

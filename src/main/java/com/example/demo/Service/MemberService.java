package com.example.demo.Service;

import com.example.demo.Entity.Member;
import com.example.demo.Repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Cacheable(value = "members", key = "'allMembers'", unless = "#result == null || #result.isEmpty()")
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Cacheable(value = "members", key = "#id")
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + id));
    }

    @CacheEvict(value = "members", key = "'allMembers'")
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    @Caching(evict = {
            @CacheEvict(value = "members", key = "#id"),
            @CacheEvict(value = "members", key = "'allMembers'")
    })
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}

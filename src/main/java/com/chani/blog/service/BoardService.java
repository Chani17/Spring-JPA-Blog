package com.chani.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chani.blog.dto.ReplySaveRequestDto;
import com.chani.blog.model.Board;
import com.chani.blog.model.Reply;
import com.chani.blog.model.RoleType;
import com.chani.blog.model.User;
import com.chani.blog.repository.BoardRepository;
import com.chani.blog.repository.ReplyRepository;
import com.chani.blog.repository.UserRepository;


@Service	
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Transactional
	public void write(Board board, User user) {
		// 글 쓰기
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> boardList(Pageable pageable) {
		// 글 목록 조회
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board boardMore(int id) {
		// 글 상세보기
		
		return boardRepository.findById(id)
				.orElseThrow(() -> {
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void deleteById(int id) {
		// 글 삭제하기
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void update(int id, Board requestBoard) {
		// 글 수정하기
		Board board = boardRepository.findById(id)
				.orElseThrow(() -> {
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); 	// 영속화 완료
		
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		
		System.out.println("boardservice: " + board.getTitle());
		System.out.println("boardservice: " + board.getContent());
		// 해당함수로 종료시(Service가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹 - 자동 업데이트가 됨 -> DB flush
	}
	
	@Transactional
	public void writeComment(ReplySaveRequestDto replySaveRequestDto) {
		// 댓글 쓰기
		int result =  replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		System.out.println("BoardService: "+ result);
	}
	
	@Transactional
	public void deleteComment(int replyId) {
		// 댓글 삭제
		replyRepository.deleteById(replyId);
	}
}

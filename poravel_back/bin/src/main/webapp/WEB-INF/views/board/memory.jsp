<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
	<%@ include file="/include/head.jsp" %>	
	<link rel="stylesheet" href="${root}/assets/css/memory.css" />	
  </head>
  <body>
  	<%@ include file="/include/nav.jsp" %>
        <main class="memory-main">
            <section class="d-flex row justify-content-center">
                <div class="container col-10 d-flex flex-wrap justify-content-center">
                    <div class="pol-item">
                        <div class="polaroid">
                            <img class="polaroid-img" src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/dXAhQuT.jpg" />
                            <div class="caption">By Joshua Sortino</div>
                        </div>
                    </div>
                    <div class="pol-item">
                        <div class="polaroid">
                            <img class="polaroid-img" src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/LZkivxR.jpg" />
                            <div class="caption">By Cole Patrick</div>
                        </div>
                    </div>
                    <div class="pol-item">
                        <div class="polaroid">
                            <img class="polaroid-img" src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/hqcMtrF.jpg" />
                            <div class="caption">By Luke Pamer</div>
                        </div>
                    </div>
                    <div class="pol-item">
                        <div class="polaroid">
                            <img class="polaroid-img" src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/l867sBU.jpg" />
                            <div class="caption">By Alissa Smith</div>
                        </div>
                    </div>
                    <div class="pol-item">
                        <div class="polaroid">
                            <img class="polaroid-img" src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/210284/7cQCk5I.jpg" />
                            <div class="caption">By Ales Krivec</div>
                        </div>
                    </div>
                    <div class="pol-item plus" onclick="">
                        <div class="polaroid">
                            <img class="polaroid-img" src="../assets/img/black.jpg" alt="" />
                            <button type="button" class="addBtn btn btn-outline-info" data-bs-toggle="modal" data-bs-target="#exampleModal">+</button>
                            <div class="caption">add memory</div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form id="memory-form">
                                <div class="mb-3">
                                    <label for="add-Picture" class="col-form-label">사진첨부하기</label>
                                    <input type="file" class="form-control" accept="image/*" id="add-picture" />
                                </div>
                                <div class="mb-3">
                                    <label for="place-title" class="col-form-label">장소이름</label>
                                    <input type="text" class="form-control" id="place-title" />
                                </div>
                                <div class="mb-3">
                                    <label for="place-date" class="col-form-label">다녀온 날짜</label>
                                    <input type="date" class="form-control" id="place-date" />
                                </div>
                                <div class="mb-3">
                                    <label for="place-message" class="col-form-label">한마디</label>
                                    <textarea class="form-control" id="place-message"></textarea>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" form="memory-form" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="submit" form="memory-form" class="btn btn-primary" data-bs-dismiss="modal">Save</button>
                        </div>
                    </div>
                </div>
            </div>
        </main>

	<%-- --%>
<script src="/assets/js/memory.js"></script>
<%@ include file="/include/footer.jsp" %>

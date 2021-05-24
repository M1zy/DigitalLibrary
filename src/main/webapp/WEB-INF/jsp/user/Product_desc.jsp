<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div >
                <div >
                    <div class="card-body">
                        <table class="table">
                            <thead>
                            <tr>

                                <th scope="col">Name</th>
                                <th scope="col">Authors</th>
                                <th scope="col">Year</th>
                                <th scope="col">Pages</th>
                                <th scope="col">Libraries</th>
                                <th scope="col">PDF</th>
                                <th scope="col">Rating</th>
                            </tr>
                            </thead>
                            <tbody style="text-align: center;">
                            <tr>
                                <td>${book.name}</td>
                                <td>${book.getAuthorsToString()}</td>
                                <td>${book.year}</td>
                                <td>${book.pages}</td>
                                <td>${book.getLibrariesToString()}</td>
                                <c:if test="${not empty book.pdf}">
                                    <td><a href="../../../resources/BookFiles/${book.pdf}" target="_blank">pdf</a></td>
                                </c:if>
                                <c:if test="${book.pdf == null}">
                                    <td>None</td>
                                </c:if>
                                <td>${baseRate}</td>
                            </tr>
                            </tbody>
                            <tr>
                                <td colspan="2" style="text-align:center; background-color: darkgrey;" >
                                    <a  href="/addingBookToArchive/${book.id}" style="font-size: 16px; text-decoration: underline">Add to Archive</a>
                                </td>
                                <td style="background-color: darkgrey;" >
                                    <form action="/addingRating/${book.id}" method="post" style="text-align: center; padding-top: 5px; ">
                                        <div class="rating_block">
                                            <input name="rating" value="5" id="rating_5" type="radio" />
                                            <label for="rating_5" class="label_rating"></label>

                                            <input name="rating" value="4" id="rating_4" type="radio" />
                                            <label for="rating_4" class="label_rating"></label>

                                            <input name="rating" value="3" id="rating_3" type="radio" />
                                            <label for="rating_3" class="label_rating"></label>

                                            <input name="rating" value="2" id="rating_2" type="radio" />
                                            <label for="rating_2" class="label_rating"></label>

                                            <input name="rating" value="1" id="rating_1" type="radio" />
                                            <label for="rating_1" class="label_rating"></label>

                                            <input type="submit" value="Submit">
                                        </div>
                                    </form>
                                </td>
                                <c:if test="${not empty userRating}">
                                <td style="font-size: 16px;background-color: darkgrey; ">Your book rating: ${userRating}</td>
                                </c:if>
                            </tr>
                            <tr>
                                <td colspan="2" style="font-size: 18px">
                                    <c:if test="${not empty error}">
                                        ${error}
                                    </c:if>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div style="height:294px; width:690px; border-style:solid; border-width:1px;">

                <div style="height:294px; width:200px; float:left; border-style:solid; border-width:1px;">
                    <img src="../../../resources/BookFiles/${book.image}" height="294" width="200" alt=""/>
                </div>
                <div style="height:294px; width:485px; float:left; border-style:solid; border-width:1px;">
                    <textarea  rows="18" cols ="72" readonly style="resize: none" >${book.description}</textarea>
                </div>
            </div>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a  href="/reviews/show/${book.id}" style="font-family: Arial; font-size: 16px; text-decoration: underline">Reviews</a>
<br/>
    <br />



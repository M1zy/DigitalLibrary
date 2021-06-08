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
        <textarea  rows="19" cols ="65" readonly style="resize: none" >${book.description}</textarea>
    </div>
</div>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/Catalog/delete/${book.id}" style="padding-right: 20px;">Delete book</a>
<a href="/Catalog/VerificationPage/verify/${book.id}">Verify</a>
<br/>
<br />



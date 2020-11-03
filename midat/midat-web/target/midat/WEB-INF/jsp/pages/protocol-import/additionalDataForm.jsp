<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}"/>

<fmt:formatDate value="${parentInfo['sample'].sampleDate}" pattern="dd.MM.yyyy" var="sampleDateFormatted"/>

<div>

<h3>
    <fmt:message key="import.protocol.additional.mainHeader" />
</h3>

<fieldset>
    <legend>
        <fmt:message key="import.protocol.additional.labProtocoleHeader" />
    </legend>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label>
                    <fmt:message key="import.protocol.additional.lab.id" />
                </label>
                    <span class="form-control-static">
                        ${parentInfo['sample'].station.stationNumber}
                    </span>
            </div>
            <div class="form-group">
                <label>
                    <fmt:message key="import.protocol.additional.lab.operator" />
                </label>
                <c:set var="labProtocolOperator" value="${parentInfo['labProtocolInfoOperator'].author}"/>
                    <span class="form-control-static">
                        ${labProtocolOperator.firstName} ${labProtocolOperator.lastName}
                    </span>
            </div>
        </div>

        <div class="col-md-6">
            <div class="form-group">
                <label>
                    <fmt:message key="import.protocol.additional.lab.date" />
                </label>
                <span class="form-control-static">
                    <fmt:formatDate value="${parentInfo['sample'].sampleDate}" pattern="dd.MM.yyyy"/>
                </span>
            </div>

            <div class="form-group">
                <label>
                    <fmt:message key="import.protocol.additional.lab.watercourse" />
                </label>
                <span class="form-control-static">

                    ${parentInfo['watercourse']}
                </span>
            </div>
        </div>
    </div>
</fieldset>






<table class="table table-striped table-bordered">
    <thead>
    <tr class="uber-header">
        <th colspan="6">
            <fmt:message key="import.protocol.additional.importedProtocols.header" />
        </th>
    </tr>
    <tr>
        <th></th>
        <th>
            <fmt:message key="import.protocol.additional.importedProtocols.date" />
        </th>
        <th>
            <fmt:message key="import.protocol.additional.importedProtocols.excelFile" />
        </th>
        <th>
            <fmt:message key="import.protocol.additional.importedProtocols.excelSheet" />
        </th>
        <th>
            <fmt:message key="import.protocol.additional.importedProtocols.operator" />
        </th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><strong>
            <fmt:message key="import.protocol.additional.importedProtocols.lab" />
        </strong></td>
        <td>
            <fmt:formatDate value="${parentInfo['sample'].dbCreationDate}" pattern="dd.MM.yyyy"/>
        </td>
        <td>
            ${parentInfo['labProtocolFile'].fileName}    ${parentInfo['labProtocolMassFile'].fileName}
        </td>
        <td>
            ${parentInfo['labProtocolFile'].sheetName}   ${parentInfo['labProtocolMassFile'].sheetName}
                <c:choose>
                    <c:when test="${parentInfo['massImport'] == 'true'}">
                     - (  <fmt:message key="menu.protocolimport.mass" /> )
                    </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>
        </td>
        <td>
            <c:set var="labProtocolDeterminator" value="${parentInfo['labProtocolInfoDeterminator'].author}"/>
            ${labProtocolDeterminator.firstName} ${labProtocolDeterminator.lastName}
        </td>
        <td>
            <c:choose>
                <c:when test="${parentInfo['massImport'] == 'true'}">

                </c:when>
                <c:otherwise>
                    <button class="deleteLabProtocolButton btn btn-xs btn-danger" href="${appRoot}/app/samples/${parentInfo['sample'].id}/protocol-type/${parentInfo['labProtocolFile'].protocolVersion.id}?_method=DELETE">
                        <i class="fa fa-trash"></i>
                    </button>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <c:if  test="${not empty parentInfo['evaluationGridInfo']}">
        <tr>
            <td><strong>
                <fmt:message key="import.protocol.additional.importedProtocols.sampleGrid" />
            </strong></td>
            <td>
                <fmt:formatDate value="${parentInfo['evaluationGridInfo'].dbCreationDate}" pattern="dd.MM.yyyy"/>
            </td>
            <td>
                    ${parentInfo['evaluationGridFile'].fileName}
            </td>
            <td>
                    ${parentInfo['evaluationGridFile'].sheetName}
            </td>
            <td>
                <c:set var="evalGridAuthor" value="${parentInfo['evaluationGridInfo'].author}"/>
                    ${evalGridAuthor.firstName} ${evalGridAuthor.lastName}

            </td>
            <td>
                <button class="deleteEvaluationGridButton btn btn-xs btn-danger"
                   href="${appRoot}/app/samples/${parentInfo['sample'].id}/protocol-type/${parentInfo['evaluationGridFile'].protocolVersion.id}?_method=DELETE">
                    <i class="fa fa-trash"></i>
                </button>
            </td>
        </tr>
    </c:if>

    <c:if  test="${not empty parentInfo['fieldProtocolFile']}">
        <tr>
            <td><strong>
                <fmt:message key="import.protocol.additional.importedProtocols.ground" />
            </strong></td>
            <td>
                <fmt:formatDate value="${parentInfo['fieldProtocolInfo'].dbCreationDate}" pattern="dd.MM.yyyy"/>
            </td>
            <td>
                    ${parentInfo['fieldProtocolFile'].fileName}
            </td>
            <td>
                    ${parentInfo['fieldProtocolFile'].sheetName}
            </td>
            <td>
                <c:set var="fieldProtocolAuthor" value="${parentInfo['fieldProtocolInfo'].author}"/>
                    ${fieldProtocolAuthor.firstName} ${fieldProtocolAuthor.lastName}
            </td>
            <td>
                <button class="deleteFieldProtocolButton btn btn-xs btn-danger" href="${appRoot}/app/samples/${parentInfo['sample'].id}/protocol-type/${parentInfo['fieldProtocolFile'].protocolVersion.id}?_method=DELETE">
                    <i class="fa fa-trash"></i>
                </button>
            </td>
        </tr>
    </c:if>

    </tbody>
</table>



<form:form action="${appRoot}/app/import/lab/validation" method="post" commandName="protocolImportHeader" enctype="multipart/form-data">

    <form:hidden path="parentId" />

    <fieldset>
        <legend>
            <fmt:message key="import.protocol.additional.form.header" />
        </legend>

        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label>
                        <fmt:message key="import.protocol.additional.form.type" />
                    </label>

                    <div class="radio">
                        <label for="dataTypeEvalGrid">
                            <input type="radio" name="protocolTypeRadio" value="${typeGrid.valueId}" id="dataTypeEvalGrid">
                            ${typeGrid.designation}
                        </label>
                    </div>
                    <div class="radio">
                        <label for="dataTypeField">
                            <input type="radio" name="protocolTypeRadio" value="${typeField.valueId}" id="dataTypeField">
                            ${typeField.designation}
                        </label>
                    </div>
                </div>

            </div>
            <div class="col-md-6">
                <div class="field-error-wrapper"><form:errors path="protocolVersionId" cssClass="fieldError"/></div>
                <div class="form-group">
                    <form:label path="protocolVersionId" >
                        <fmt:message key="import.protocol.additional.form.version" />
                    </form:label>
                    <form:select path="protocolVersionId" class="form-control"></form:select>
                </div>
            </div>
        </div>

        <hr/>

        <div class="row">
            <div class="col-md-3">
                <div class="field-error-wrapper"><form:errors path="languageId" cssClass="fieldError"/></div>
                <div class="form-group">
                    <form:label path="languageId" >
                        <fmt:message key="import.protocol.additional.form.language" />
                    </form:label>
                    <form:select path="languageId" class="form-control">
                        <form:options items="${formValues['languages']}" itemLabel="designation" itemValue="id"/>
                    </form:select>
                </div>
            </div>
            <div class="col-md-6">
                <div class="field-error-wrapper"><form:errors path="samplePersonId" cssClass="fieldError"/></div>
                <div class="form-group">
                    <form:label path="samplePersonId" >
                        <fmt:message key="import.protocol.additional.form.operator" />
                    </form:label>

                    <form:select path="samplePersonId" class="form-control">
                        <form:option value="" label="-"/>
                        <form:options items="${formValues['persons']}" itemLabel="names" itemValue="id"/>
                    </form:select>
                </div>
            </div>
            <div class="col-md-3">
                <div class="field-error-wrapper"><form:errors path="analysisDate" cssClass="fieldError"/></div>
                <div class="form-group">
                    <form:label path="analysisDate" >
                        <fmt:message key="import.protocol.additional.form.date" />
                    </form:label>

                    <form:input path="analysisDate" type="text" class="date form-control" id="analysisDate" />
                </div>
            </div>
        </div>

        <hr/>

        <div class="row">
            <div class="col-md-6">
                <div class="field-error-wrapper"><form:errors path="excelFileBytes" class="fieldError"/></div>
                <div class="form-group">
                    <label>
                        <fmt:message key="import.protocol.additional.form.excelFile" />
                        <span class="fileName"></span>
                    </label>
                    <span class="file-input-wrapper btn btn-default">
                        <input type="file" name="file" id="excelFileInput" />
                        <i class="fa fa-folder-open-o"></i>
                        <span class="text">
                            <fmt:message key="import.protocol.additional.form.filePicker" />
                        </span>
                        <i class="pull-right fa fa-cog fa-spin"></i>
                    </span>
                </div>
            </div>
            <div class="col-md-6">
                <div class="field-error-wrapper"><form:errors path="excelSheetName" class="fieldError"/></div>
                <div class="form-group">
                    <form:label path="excelSheetName">
                        <fmt:message key="import.protocol.additional.form.excelSheet" />
                    </form:label>
                    <form:select path="excelSheetName" disabled="true" class="form-control" id="excelSheetName"/>
                </div>
            </div>
        </div>

        <hr/>

        <form:button id="submitButton" class="btn btn-primary pull-right">
            <i class="fa fa-check" ></i>
            <span class="text">
                <fmt:message key="import.protocol.additional.form.submit" />
            </span>
        </form:button>

    </fieldset>

</form:form>

</div>
<script>
    document.getElementById("languageId").addEventListener(
        'change',
        function() {
            try{
                localStorage.setItem('selectedLang',this.value);
            } catch(er){
                console.log(er)
            }
        },
        false
    );

    try{
        var lang = localStorage.getItem('selectedLang');
        if(lang){
            document.getElementById("languageId").value =lang;
        }
    }catch(err){
        console.log(err)
    }
</script>
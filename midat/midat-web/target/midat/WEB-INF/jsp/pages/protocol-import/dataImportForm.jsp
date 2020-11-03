<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="appRoot" value="${pageContext.request.contextPath}"/>

<c:if test="${isWaitingForMassTask}">
    <script>
        midatFlags = {};
        midatFlags.isWaitingForMassTask = true;
        midatFlags.massTaskKey = '${massImportTaskKey}';

    </script>
</c:if>



<c:choose>
    <c:when test="${mass}">
        <c:set var="formUrl" value="${appRoot}/app/import/mass/validation"/>
    </c:when>
    <c:otherwise>
        <c:set var="formUrl" value="${appRoot}/app/import/lab/validation"/>
    </c:otherwise>
</c:choose>


<div>

<h3>
    <fmt:message key="import.protocol.lab.title"/>
</h3>

<form:form action="${formUrl}" method="post" commandName="protocolImportHeader" enctype="multipart/form-data">

    <fieldset>
        <legend>
            <fmt:message key="import.protocol.lab.source"/>
        </legend>
        <div class="row">
            <div class="col-md-6">
                <div class="field-error-wrapper"><form:errors path="principalInstituionId" class="fieldError"/></div>
                <div class="form-group">
                    <form:label path="principalInstituionId" class="control-label">
                        <fmt:message key="import.protocol.lab.principalInstitution"/>
                    </form:label>
                    <form:select path="principalInstituionId" items="${formValues.institutions}" itemLabel="name"
                                 itemValue="id" class="form-control"/>
                </div>
            </div>

            <div class="col-md-6">
                <div class="field-error-wrapper"><form:errors path="mandataryInstitutionId" class="fieldError"/></div>
                <div class="form-group">
                    <form:label path="mandataryInstitutionId" class="control-label">
                        <fmt:message key="import.protocol.lab.mandataryInstitution"/>
                    </form:label>
                    <form:select path="mandataryInstitutionId" items="${formValues.institutions}" itemLabel="name"
                                 itemValue="id" class="form-control"/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-4">
                <div class="field-error-wrapper"><form:errors path="protocolVersionId" class="fieldError"/></div>
                <div class="form-group">
                    <form:label path="protocolVersionId" class="control-label">
                        <c:choose>
                            <c:when test="${formValues.mass}">
                                <fmt:message key="import.protocol.mass.version"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="import.protocol.lab.version"/>
                            </c:otherwise>
                        </c:choose>
                    </form:label>
                    <form:select path="protocolVersionId" items="${formValues.protocolVersions}"
                                 itemLabel="localizedDescription" itemValue="id" class="form-control"/>
                </div>
            </div>

            <div class="col-md-4">
                <div class="field-error-wrapper"><form:errors path="documentUrl" class="fieldError"/></div>
                <div class="form-group">
                    <form:label path="documentUrl" class="control-label">
                        <fmt:message key="import.protocol.lab.url"/>
                    </form:label>
                    <form:input path="documentUrl" type="text" class="form-control"
                                placeholder="URL du document (optionnel)"/>
                </div>
            </div>

            <div class="col-md-4">
                <div class="field-error-wrapper"><form:errors path="languageId" class="fieldError"/></div>
                <div class="form-group">
                    <form:label path="languageId" class="control-label">
                        <fmt:message key="import.protocol.lab.language"/>
                    </form:label>
                    <form:select path="languageId" items="${formValues.languages}" itemLabel="designation"
                                 itemValue="id" class="form-control"/>
                </div>
            </div>
        </div>

    </fieldset>

    <%-- the person fields are temporary hidden see MIDAT-91
    <fieldset>
        <legend>
            <fmt:message key="import.protocol.lab.persons" />
        </legend>
        <div class="row">
            <div class="col-md-6">
                <div class="field-error-wrapper"><form:errors path="samplePersonId" class="fieldError"/></div>
                <div class="form-group">
                    <form:label path="samplePersonId" class="control-label">
                        <fmt:message key="import.protocol.lab.samplePerson" />
                    </form:label>
                    <form:select path="samplePersonId" class="form-control" >
                        <form:option value="">-</form:option>
                        <form:options items="${formValues.persons}" itemLabel="names" itemValue="id"/>
                    </form:select>
                </div>
            </div>

            <div class="col-md-6">
                <div class="field-error-wrapper"><form:errors path="analysisPersonId" class="fieldError"/></div>
                <div class="form-group">
                    <form:label path="analysisPersonId" class="control-label">
                        <fmt:message key="import.protocol.lab.analysisPerson" />
                    </form:label>
                    <form:select path="analysisPersonId" class="form-control">
                        <form:option value="">-</form:option>
                        <form:options items="${formValues.persons}" itemLabel="names" itemValue="id"/>
                    </form:select>
                </div>
            </div>
        </div>
    </fieldset>
    --%>

    <fieldset>
        <legend>
            <fmt:message key="import.protocol.lab.details"/>
        </legend>

        <div class="row">


            <c:choose>
                <c:when test="${mass}">
                    <!-- projects is not available for mass -->
                </c:when>
                <c:otherwise>
                    <!-- projects only for protocol importation -->
                    <div class="col-md-3">
                        <div class="field-error-wrapper"><form:errors path="sampleProjectId" class="fieldError"/></div>
                        <div class="form-group">
                            <form:label path="sampleProjectId" class="control-label">
                                <fmt:message key="search.resulttable.project"/>
                            </form:label>

                            <form:select path="sampleProjectId" class="form-control">
                                <option value="">&nbsp; </option>

                                <c:forEach items="${formValues.projects}" var="project">
                                    <form:option value="${project.id}" label="${project.code} - ${project.designation}"/>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="col-md-5">
                <div class="field-error-wrapper"><form:errors path="referenceSystemId" class="fieldError"/></div>
                <div class="form-group">
                    <form:label path="referenceSystemId" class="control-label">
                        <fmt:message key="import.protocol.lab.referenceSystem"/>
                    </form:label>
                    <form:select path="referenceSystemId" items="${formValues.referenceSystems}" itemLabel="designation"
                                 itemValue="valueId" class="form-control"/>
                </div>
            </div>
            <div class="col-md-4">
                <div class="field-error-wrapper"><form:errors path="precisionLevelId" class="fieldError"/></div>
                <div class="form-group">
                    <form:label path="precisionLevelId" class="control-label">
                        <fmt:message key="import.protocol.lab.precisionLevel"/>
                    </form:label>
                    <form:select path="precisionLevelId" class="form-control"/>
                </div>
            </div>
        </div>

        <!-- remarks section  -->
        <c:choose>
            <c:when test="${mass}">
                <!-- remarks are not availbale for mass importation -->
            </c:when>
            <c:otherwise>
                <!-- add remarks for protocol importation -->
                <hr/>
                <div class="row">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <label>
                                <fmt:message key="details.sample.remarks"/>
                            </label>
                        </div>
                        <c:forEach items="${formValues.comments}" var="comment" varStatus="c">
                            <div class="col-md-8 col-sm-6">
                                <div class="checkbox">
                                    <label for="commentCheckbox${comment.valueId}">
                                        <form:checkbox path="commentIds" value="${comment.valueId}"
                                                       id="Checkbox${comment.valueId}"/>${comment.designation}
                                    </label>
                                </div>
                            </div>
                        </c:forEach>
                        <div class="col-md-8 col-sm-6">
                            <div class="checkbox">
                                <label for="commentOtherChk">
                                    <form:checkbox path="commentOtherChk" value="commentOtherChk" id="commentOtherChk"/>
                                    <fmt:message key="search.details.sample.remarks.others"/>&nbsp;<form:input
                                        path="commentOther" type="text" id="commentOther" size="80px"
                                        name="commentOther"/>
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>


    </fieldset>

    </div>


    <fieldset>
        <legend>
            <fmt:message key="import.protocol.lab.file"/>
        </legend>

        <div class="row">

            <div class="col-md-6">
                <div class="field-error-wrapper"><form:errors path="excelFileBytes" class="fieldError"/></div>
                <div class="form-group">
                    <label class="control-label">
                        <fmt:message key="import.protocol.lab.excelFile"/>
                        <span class="fileName"></span>
                    </label>
                    <br/>

                    <span class="file-input-wrapper btn btn-default">
                        <input type="file" name="file" id="excelFileInput"/>
                        <i class="fa fa-folder-open-o"></i>
                        <span class="text">
                            <fmt:message key="import.protocol.lab.excelFile.pick"/>
                        </span>
                        <i class="pull-right fa fa-cog fa-spin"></i>
                    </span>
                </div>
            </div>

            <div class="col-md-6">
                <div class="field-error-wrapper"><form:errors path="excelSheetName" class="fieldError"/></div>
                <div class="form-group">
                    <form:label path="excelSheetName" class="control-label">
                        <fmt:message key="import.protocol.lab.excelSheetName"/>
                    </form:label>
                    <form:select path="excelSheetName" disabled="true" class="form-control"/>
                </div>
            </div>
        </div>
    </fieldset>

    <hr/>

    <form:button id="submitButton" class="btn btn-primary pull-right">
        <i class="fa fa-check"></i>
        <span class="text">
            <fmt:message key="import.protocol.lab.submit"/>
        </span>
    </form:button>

    </div>
</form:form>

</div>

<script type="text/html" id="import-in-progress-modal">
    <div class="dialog-form modal fade">
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <i class="fa fa-cog fa-spin pull-right"></i>
                    <h4>
                        <fmt:message key="import.protocol.mass.loading.title"/>
                    </h4>
                </div>

                <div class="modal-body">
                    <p>
                        <fmt:message key="import.protocol.mass.loading.patient"/>
                    </p>
                    <p>
                        <fmt:message key="import.protocol.mass.loading.current"/>
                        <span id="current-process-state"></span>
                    </p>

                    <div class="progress">
                        <div class="progress-bar" role="progressbar" style="width: 3%;">

                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</script>


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

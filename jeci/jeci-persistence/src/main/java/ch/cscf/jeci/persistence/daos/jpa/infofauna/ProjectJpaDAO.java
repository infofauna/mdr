package ch.cscf.jeci.persistence.daos.jpa.infofauna;

import ch.cscf.jeci.domain.entities.base.EntityStatus;
import ch.cscf.jeci.domain.entities.base.Project;
import ch.cscf.jeci.persistence.SortOrder;
import ch.cscf.jeci.persistence.daos.Page;
import ch.cscf.jeci.persistence.daos.interfaces.infofauna.ProjectDAO;
import ch.cscf.jeci.persistence.daos.jpa.GenericJpaEntityDAO;
import com.google.common.base.Strings;
import org.springframework.stereotype.Repository;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: henryp
 */
@Repository
public class ProjectJpaDAO extends GenericJpaEntityDAO<Project> implements ProjectDAO {



    @Override
    public List<Project> listFilterByMultiCriterias(String orderByCol, SortOrder sortOrder, Page page,
                                                    String codeName, String institutionName) {

        //make sure no null values
        codeName = Strings.nullToEmpty(codeName).trim();
        institutionName = Strings.nullToEmpty(institutionName).trim();

        List<Predicate> predicates = new ArrayList<>();
        CriteriaBuilder builder = getEm().getCriteriaBuilder();
        CriteriaQuery criteria =  builder.createQuery(Project.class);
        Root<Project> root = criteria.from(Project.class);
        root.alias(ROOT_ALIAS);

        Expression<Boolean> statusPath = root.get("status");
        predicates.add(builder.and(builder.equal(statusPath, EntityStatus.ACTIVE)));

        if(codeName.trim().length()>0){
            codeName =prepareStringForLikeSearch(codeName);

            Expression<String> codePath = root.get("code");
            Expression<String> namePath = root.get("designation");

            Expression<String> lowerCodePath = builder.lower(codePath);
            Expression<String> lowerNamePath = builder.lower(namePath);

            predicates.add(builder.or(builder.like(lowerCodePath, codeName), builder.like(lowerNamePath, codeName)));

        }

        if(institutionName.trim().length()>0){
            institutionName =prepareStringForLikeSearch(institutionName);

            Expression<String> mandataryInstitutionPath = root.get("mandataryInstitution").get("name");
            Expression<String> principalInstitutionPath = root.get("principalInstitution").get("name");

            Expression<String> lowerMandataryInstitutionPath = builder.lower(mandataryInstitutionPath);
            Expression<String> lowerPrincipalInstitutionPath = builder.lower(principalInstitutionPath);

            predicates.add(builder.or(builder.like(lowerMandataryInstitutionPath, institutionName), builder.like(lowerPrincipalInstitutionPath, institutionName)));

        }

        //predicates.add(builder.or(builder.like(lowerDesignationPath, nameOrDescription), builder.like(lowerDescriptionPath, nameOrDescription)));

        return super.listImpl(orderByCol, sortOrder, page, predicates, null);
    }
}

package ch.cscf.jeci.domain.entities.systematics;

import javax.persistence.*;

@Entity
@SqlResultSetMapping(
    name="taxonPathMapping",
    entities=@EntityResult(
            entityClass = TaxonPathDTO.class,
            fields = {
                @FieldResult(name="id",         column="id"),
                @FieldResult(name="path",       column="path"),
                @FieldResult(name="authorYear", column="authoryear")
            }
    )
)
@NamedNativeQueries({
    @NamedNativeQuery(name = TaxonPathDTO.QUERY_SEARCH_TAXON_PATH,
        query =
            "SELECT" +
             " syv.syv_id  id" +
            ", SUBSTR(SYS_CONNECT_BY_PATH(syd.syd_designation, '> '), 3)  path" +
            ", syv.syv_authoryear  authoryear" +
             " FROM infofauna.systvalue        syv" +
                 ", infofauna.systdesignation  syd" +
            " WHERE syd.syd_syv_id = syv.syv_id" +
              " AND syd.syd_lan_id = :languageId" +
              " AND LOWER(syd.syd_designation) LIKE LOWER(:designation)||'%'" +
            " START WITH syv.syv_syv_id IS NULL" +
            " CONNECT BY PRIOR syv.syv_id = syv.syv_syv_id" +
            " ORDER SIBLINGS BY syd.syd_designation",
        resultSetMapping = "taxonPathMapping"
    ),
    @NamedNativeQuery(name=TaxonPathDTO.QUERY_GET_TAXON_PATH,
        query =
            "SELECT" +
             " syv.syv_id  id" +
            ", SUBSTR(SYS_CONNECT_BY_PATH(syd.syd_designation, '> '), 3)  path" +
            ", syv.syv_authoryear  authoryear" +
             " FROM infofauna.systvalue        syv" +
                 ", infofauna.systdesignation  syd" +
            " WHERE syd.syd_syv_id = syv.syv_id" +
              " AND syd.syd_lan_id = :languageId" +
              " AND syv.syv_id = :taxonId" +
            " START WITH syv.syv_syv_id IS NULL" +
            " CONNECT BY PRIOR syv.syv_id = syv.syv_syv_id",
        resultSetMapping = "taxonPathMapping"
    ),
    @NamedNativeQuery(name=TaxonPathDTO.QUERY_SYNONYMS_TAXON_PATH,
        query =
            "SELECT" +
             " syv.syv_id  id" +
            ", SUBSTR(SYS_CONNECT_BY_PATH(syd.syd_designation, '> '), 3)  path" +
            ", syv.syv_authoryear  authoryear" +
             " FROM infofauna.systvalue        syv" +
                 ", infofauna.systdesignation  syd" +
            " WHERE syd.syd_syv_id = syv.syv_id" +
              " AND syd.syd_lan_id = :languageId" +
              " AND syv.syv_syv_id_synonymfor = :taxonId" +
            " START WITH syv.syv_syv_id IS NULL" +
            " CONNECT BY PRIOR syv.syv_id = syv.syv_syv_id" +
            " ORDER SIBLINGS BY syd.syd_designation",
        resultSetMapping = "taxonPathMapping"
    )
})
public class TaxonPathDTO {

    public static final String QUERY_SEARCH_TAXON_PATH = "searchTaxonPathQuery";
    public static final String QUERY_GET_TAXON_PATH = "getTaxonPathQuery";
    public static final String QUERY_SYNONYMS_TAXON_PATH = "synonymsTaxonPathQuery";

    private Long id;
    private String path;
    private String authorYear;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAuthorYear() {
        return authorYear;
    }

    public void setAuthorYear(String authorYear) {
        this.authorYear = authorYear;
    }
}

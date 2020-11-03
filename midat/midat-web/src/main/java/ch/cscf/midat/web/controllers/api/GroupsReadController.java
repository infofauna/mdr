package ch.cscf.midat.web.controllers.api;

import ch.cscf.jeci.domain.entities.security.Group;
import ch.cscf.jeci.services.security.interfaces.GroupReadService;
import ch.unine.sitel.o2m.Mapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by henryp on 06/03/15.
 */
@Controller
@RequestMapping("/api/groups")
public class GroupsReadController {

    @Autowired
    private GroupReadService groupReadService;

    @Autowired
    Mapper mapper;

    @RequestMapping(value = "/user-groups-for-samples-search", method = RequestMethod.GET)
    public @ResponseBody
    List<Map<String, Object>> getUserGroupsForSampleSearch(){
        List<Group> groups =  groupReadService.getGroupsAuthorizedForUserForSampleSearch();
        return mapper.mapCollection(groups, Lists.newArrayList("name", "localizedDescription", "id"));
    }

}

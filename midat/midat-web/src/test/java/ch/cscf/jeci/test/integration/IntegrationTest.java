package ch.cscf.jeci.test.integration;

import ch.cscf.jeci.domain.entities.security.User;
import com.google.common.collect.Lists;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.ExecutionException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author: henryp
 */
@ContextConfiguration(locations = {"classpath:META-INF/jeci-services-context.xml", "classpath:META-INF/midat-app-context.xml", "classpath:test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class IntegrationTest {

    private ThreadState _threadState;
    protected Subject _mockSubject;

    @Before
    public void before() {

        //TODO: use mockito instead of this "implementation" ?
        _mockSubject = new Subject() {
            @Override
            public Object getPrincipal() {
                User user = new User();
                user.setUsername("admin");
                user.setId(4l);
                return user;
            }

            @Override
            public PrincipalCollection getPrincipals() {
                return null;
            }

            @Override
            public boolean isPermitted(String permission) {
                return false;
            }

            @Override
            public boolean isPermitted(Permission permission) {
                return false;
            }

            @Override
            public boolean[] isPermitted(String... permissions) {
                return new boolean[0];
            }

            @Override
            public boolean[] isPermitted(List<Permission> permissions) {
                return new boolean[0];
            }

            @Override
            public boolean isPermittedAll(String... permissions) {
                return false;
            }

            @Override
            public boolean isPermittedAll(Collection<Permission> permissions) {
                return false;
            }

            @Override
            public void checkPermission(String permission) throws AuthorizationException {

            }

            @Override
            public void checkPermission(Permission permission) throws AuthorizationException {

            }

            @Override
            public void checkPermissions(String... permissions) throws AuthorizationException {

            }

            @Override
            public void checkPermissions(Collection<Permission> permissions) throws AuthorizationException {

            }

            @Override
            public boolean hasRole(String roleIdentifier) {
                return false;
            }

            @Override
            public boolean[] hasRoles(List<String> roleIdentifiers) {
                return new boolean[0];
            }

            @Override
            public boolean hasAllRoles(Collection<String> roleIdentifiers) {
                return false;
            }

            @Override
            public void checkRole(String roleIdentifier) throws AuthorizationException {

            }

            @Override
            public void checkRoles(Collection<String> roleIdentifiers) throws AuthorizationException {

            }

            @Override
            public void checkRoles(String... roleIdentifiers) throws AuthorizationException {

            }

            @Override
            public void login(AuthenticationToken token) throws AuthenticationException {

            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public boolean isRemembered() {
                return false;
            }

            @Override
            public Session getSession() {
                return null;
            }

            @Override
            public Session getSession(boolean create) {
                return null;
            }

            @Override
            public void logout() {

            }

            @Override
            public <V> V execute(Callable<V> callable) throws ExecutionException {
                return null;
            }

            @Override
            public void execute(Runnable runnable) {

            }

            @Override
            public <V> Callable<V> associateWith(Callable<V> callable) {
                return null;
            }

            @Override
            public Runnable associateWith(Runnable runnable) {
                return null;
            }

            @Override
            public void runAs(PrincipalCollection principals) throws NullPointerException, IllegalStateException {

            }

            @Override
            public boolean isRunAs() {
                return false;
            }

            @Override
            public PrincipalCollection getPreviousPrincipals() {
                return null;
            }

            @Override
            public PrincipalCollection releaseRunAs() {
                return null;
            }
        };
        _threadState = new SubjectThreadState(_mockSubject);
        _threadState.bind();
    }


}

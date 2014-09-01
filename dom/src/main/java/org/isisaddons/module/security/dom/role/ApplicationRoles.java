/*
 *  Copyright 2014 Dan Haywood
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.isisaddons.module.security.dom.role;

import java.util.List;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.objectstore.jdo.applib.service.JdoColumnLength;

@Named("Roles")
@DomainService(menuOrder = "90.2", repositoryFor = ApplicationRole.class)
public class ApplicationRoles extends AbstractFactoryAndRepository {

    public String iconName() {
        return "applicationRole";
    }

    @MemberOrder(sequence = "20.1")
    @ActionSemantics(Of.SAFE)
    public ApplicationRole findRoleByName(final String name) {
        return uniqueMatch(new QueryDefault<ApplicationRole>(ApplicationRole.class, "findByName", "name", name));
    }

    @MemberOrder(sequence = "20.2")
    @ActionSemantics(Of.NON_IDEMPOTENT)
    public ApplicationRole newRole(
            final @Named("Name") String name,
            final @Named("Description") @Optional @TypicalLength(50) @MaxLength(JdoColumnLength.DESCRIPTION) String description) {
        ApplicationRole role = newTransientInstance(ApplicationRole.class);
        role.setName(name);
        persist(role);
        return role;
    }

    @MemberOrder(sequence = "20.2")
    @Prototype
    @ActionSemantics(Of.SAFE)
    public List<ApplicationRole> allRoles() {
        return allInstances(ApplicationRole.class);
    }


    @Programmatic // not part of metamodel
    public List<ApplicationRole> autoComplete(final String name) {
        return allMatches(
                new QueryDefault<ApplicationRole>(ApplicationRole.class,
                        "findByNameContaining",
                        "name", name));
    }

}
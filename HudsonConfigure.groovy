// java -jar jenkins-cli.jar -s http://localhost:8080/hudson/ groovy HudsonConfigure.groovy --username <user> --password <passwd>

import hudson.model.*
import org.eclipse.hudson.security.team.*

userName = "testUser3"
userPassword = "123"
teamName = "T" + userName
viewName = userName

hudsonInstance = Hudson.getInstance()

//Create user
hudsonInstance.securityRealm.createAccount(userName, userPassword)

//Create team 
teamManager = hudsonInstance.getTeamManager()
team = teamManager.createTeam(teamName)

//Add 'admin' as team admin
teamMemberAdmin = new TeamMember()
teamMemberAdmin.setName("admin")
teamMemberAdmin.setAsTeamAdmin(true)
team.addMember(teamMemberAdmin)

//Add user to team
teamMember = new TeamMember()
teamMember.setName(userName)
teamMember.addPermission("create")
teamMember.addPermission("delete")
teamMember.addPermission("configure")
teamMember.addPermission("build")
team.addMember(teamMember)

//Create view
listView = new ListView(viewName)
listView.includeRegex = teamName + ".*"
hudsonInstance.addView(listView)

//Add view to team
teamManager.addView(team, viewName)
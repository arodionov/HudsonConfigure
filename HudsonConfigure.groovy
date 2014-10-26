// java -jar jenkins-cli.jar -s http://localhost:8080/hudson/ groovy HudsonConfigure.groovy --username <user> --password <passwd>

import hudson.model.*
import org.eclipse.hudson.security.team.*

def userFullName = "Test Test"
def userEmail = "andrey.rodionov@gmail.com"
def userName = "ys14" + userFullName.split(" ")[1].toLowerCase()
def userPassword = "123"
def teamName = "T" + userName
def viewName = userName

def hudsonInstance = Hudson.getInstance()

//Create user
def user = hudsonInstance.securityRealm.createAccount(userName, userPassword)
user.setFullName(userFullName)
def email_param = new hudson.tasks.Mailer.UserProperty(userEmail)
user.addProperty(email_param)
user.save()

//Create team 
def teamManager = hudsonInstance.getTeamManager()
def team = teamManager.createTeam(teamName)

//Add 'admin' as team admin
def teamMemberAdmin = new TeamMember()
teamMemberAdmin.setName("admin")
teamMemberAdmin.setAsTeamAdmin(true)
team.addMember(teamMemberAdmin)

//Add user to team
def teamMember = new TeamMember()
teamMember.setName(userName)
teamMember.addPermission("create")
teamMember.addPermission("delete")
teamMember.addPermission("configure")
teamMember.addPermission("build")
team.addMember(teamMember)

//Create view
def listView = new ListView(viewName)
listView.includeRegex = teamName + ".*"
hudsonInstance.addView(listView)

//Add view to team
teamManager.addView(team, viewName)
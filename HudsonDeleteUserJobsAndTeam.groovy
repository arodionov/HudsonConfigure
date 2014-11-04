import hudson.model.*
import org.eclipse.hudson.security.team.*

def userName = "test"

def hudsonInstance = Hudson.getInstance()
hudsonInstance.items.findAll{ job -> job.name.startsWith("T" + userName + ".") }.each{ job -> job.delete() }
hudsonInstance.getTeamManager().deleteTeam("T" + userName)
hudsonInstance.getUser(userName).delete()
hudsonInstance.deleteView(hudsonInstance.getView(userName))
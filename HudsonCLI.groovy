//groovy -cp hudson-cli.jar HudsonCLI.groovy

import hudson.cli.*

url = 'http://localhost:8080/hudson/'
cli = new CLI(new URL(url))
//args = Arrays.asList("list-teams", "--username", "admin", "--password", "admin")
args = Arrays.asList("groovy", "HudsonConfigure.groovy", "--username", "admin", "--password", "admin")
cli.execute(args, System.in, System.out, System.err)
cli.close()
package tnt.evoscada.pluginformavensettings;


import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.idea.maven.execution.MavenRunner;
import org.jetbrains.idea.maven.project.MavenProjectsManager;


public class SetMavenSettingsStartupActivity implements com.intellij.openapi.startup.StartupActivity {

    @Override
    public void runActivity(@NotNull Project project) {

        var mavenRunnerSettings = MavenRunner.getInstance(project).getState();
        MavenProjectsManager mavenProjectsManager = ServiceManager.getService(project, MavenProjectsManager.class);

        if (mavenRunnerSettings.getVmOptions().isEmpty() && mavenProjectsManager.getImportingSettings().getVmOptionsForImporter().isEmpty() &&
                mavenProjectsManager != null && mavenProjectsManager.isMavenizedProject()) {

            var vmOptions = "-Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true";
            mavenProjectsManager.getImportingSettings().setVmOptionsForImporter(vmOptions);
            mavenRunnerSettings.setVmOptions(vmOptions);
        }
    }
}

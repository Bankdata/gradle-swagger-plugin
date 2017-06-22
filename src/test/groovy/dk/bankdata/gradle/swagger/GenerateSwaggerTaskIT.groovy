package dk.bankdata.gradle.swagger

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test
import org.yaml.snakeyaml.Yaml

class GenerateSwaggerTaskIT {

    @Test
    void testTypicalGeneration() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'dk.bankdata.swagger'
        project.swagger {
            resourcePackages = ['dk.bankdata.gradle.swagger.example']
            schemes = ['https']
            info {
                title = 'Swagger Plugin Test'
                contact {
                    name = "Bankdata"
                }
            }
        }
        project.tasks.swaggerGenerate.generate()

        def artifact = project.configurations.archives.allArtifacts.find { artifact -> artifact.classifier == 'swagger' }
        assert artifact != null

        Yaml parser = new Yaml()
        Map content = parser.load(artifact.file.text)
        assert content.swagger == '2.0'
        assert content.info.title == 'Swagger Plugin Test'
        assert content.info.contact.name == 'Bankdata'
        assert content.paths.size() == 2
        assert content.definitions.size() == 5
    }

    @Test
    void testFullConfig() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'dk.bankdata.swagger'
        project.swagger {
            resourcePackages = ['dk.bankdata.gradle.swagger.example']
            schemes = ['https']
            info {
                title = 'Swagger Plugin Full'
                version = '1.0.0'
                description = 'This service bla bla bla'
                termsOfService = 'Terms'
                contact {
                    name = 'Bankdata'
                    url = 'https://www.bankdata.dk'
                    email = 'bankdata@e.mail'
                }
                license {
                    name = 'MIT'
                    url = 'http://mit'
                }
            }
        }
        project.tasks.swaggerGenerate.generate()

        def artifact = project.configurations.archives.allArtifacts.find { artifact -> artifact.classifier == 'swagger' }
        assert artifact != null

        Yaml parser = new Yaml()
        Map content = parser.load(artifact.file.text)
        assert content.info.title == 'Swagger Plugin Full'
        assert content.info.version == '1.0.0'
        assert content.info.description == 'This service bla bla bla'
        assert content.info.termsOfService == 'Terms'
        assert content.info.contact.name == 'Bankdata'
        assert content.info.contact.url == 'https://www.bankdata.dk'
        assert content.info.contact.email == 'bankdata@e.mail'
        assert content.info.license.name == 'MIT'
        assert content.info.license.url == 'http://mit'
    }

    @Test
    void testSwaggerDefinition() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'dk.bankdata.swagger'
        project.swagger {
            resourcePackages = ['dk.bankdata.gradle.swagger.example.alternate']
        }
        project.tasks.swaggerGenerate.generate()

        def artifact = project.configurations.archives.allArtifacts.find { artifact -> artifact.classifier == 'swagger' }
        assert artifact != null

        Yaml parser = new Yaml()
        Map content = parser.load(artifact.file.text)
        assert content.info.title == 'My Title'
    }

}

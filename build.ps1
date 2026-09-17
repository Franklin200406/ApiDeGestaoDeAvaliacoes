$ErrorActionPreference = 'Stop'

$Jdk = 'C:\Program Files\Java\jdk-23.0.2'

if (-not (Test-Path -LiteralPath (Join-Path $Jdk 'bin\java.exe'))) {
    Write-Error "JDK nao encontrado em $Jdk"
    exit 1
}

$env:JAVA_HOME = $Jdk
$env:Path = "$Jdk\bin;" + $env:Path

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
& '.\mvnw.cmd' -f (Join-Path $root 'pom.xml') clean compile 2>&1 | Out-String | ForEach-Object { [Console]::WriteLine($_) }

exit $LASTEXITCODE

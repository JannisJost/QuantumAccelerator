
$current =$MyInvocation.MyCommand.Path
$current = (Get-Item $current ).DirectoryName
[array]$file_data = Get-Content $current/CommunicationToJava/tempDirectories.tmp
foreach($value in $file_data){
try{
Remove-Item $value -Recurse -Force -ErrorAction Stop
}
catch{
Write-Host 'exception caught'
Remove-Item $value -Recurse -ErrorAction SilentlyContinue
}
}



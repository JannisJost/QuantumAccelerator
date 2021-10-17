
[array]$allConfigs = cmd /c "powercfg /L"
$current =$MyInvocation.MyCommand.Path
$currentDirectory = (Get-Item $current ).DirectoryName
foreach($value in $allConfigs){
if($value -like '*(QuantumAccelerator_Eco*'){
$PowerPlan = $value
}
}
if ([string]::IsNullOrEmpty($PowerPlan)){
Write-Host Is null $PowerPlan
$PowDirectory = $currentDirectory + '\PowFiles\QuantumAcceleratorEco.pow'
powercfg -import $PowDirectory
[array]$allConfigs = cmd /c "powercfg /L"
foreach($value in $allConfigs){
if($value -like '*(QuantumAccelerator_Eco*'){
$PowerPlan = $value
}
}
}
Write-Host already exists
$regEx = '(\{){0,1}[0-9a-fA-F]{8}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{12}(\}){0,1}'
$GUID = [regex]::Match($PowerPlan,$regEx).Value
powercfg /S $GUID
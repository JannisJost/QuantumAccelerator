Enable-PnpDevice -InstanceId (Get-PnpDevice -FriendlyName *webcam* -Class Camera ).InstanceId -Confirm:$false

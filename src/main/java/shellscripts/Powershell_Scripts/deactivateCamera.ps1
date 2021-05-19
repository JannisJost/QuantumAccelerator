Disable-PnpDevice -InstanceId (Get-PnpDevice -FriendlyName *webcam* -Class Camera -Status OK).InstanceId -Confirm:$false


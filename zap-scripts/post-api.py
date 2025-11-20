def zap_started(zap, target):
    zap.urlopen(target)
    zap.core.send_request(
        {
            "method": "POST",
            "url": f"{target}/bp",
            "body": '{"systolic":120,"diastolic":80}',
            "headers": {"Content-Type": "application/json"}
        }
    )
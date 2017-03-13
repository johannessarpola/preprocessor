import pywikibot
site = pywikibot.Site('en', 'wikipedia')  # The site we want to run our bot on
page = pywikibot.Page(site, 'Wikipedia:Sandbox')
print(pywikibot.Category('en', 'Post–Russian Empire states'))
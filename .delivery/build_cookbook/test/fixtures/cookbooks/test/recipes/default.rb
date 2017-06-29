%w(unit lint syntax).each do |phase|
  # TODO: This works on Linux/Unix. Not Windows.
  execute "HOME=/home/vagrant delivery job verify #{phase} --SERVER localhost --ent test --ORG kitchen" do
    cwd '/tmp/repo-data'
    user 'vagrant'
  end
end
